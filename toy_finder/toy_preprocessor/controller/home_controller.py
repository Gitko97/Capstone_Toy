import sys
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QFileDialog
from matplotlib import pyplot as plt
from toy_finder.toy_preprocessor.file.file_in_out import FileInOut
from toy_finder.toy_preprocessor.image_utils.preprocessor import Image_PreProcessor
from toy_finder.toy_preprocessor.ui.ui import Ui_Dialog
from toy_finder.toy_preprocessor.controller.grabcut_controller import GrabCutController
import cv2

import os
google_api_key_pos = "/Users/joonhyoungjeon/Downloads/toyvision-adb9631350f2.json"
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = google_api_key_pos

class Controller(object):
    def __init__(self, file_extension=".png"):
        self.file_paths = []
        self.dicom_files = []
        self.file_io = FileInOut(file_extension)
        self.image_preprocessor = Image_PreProcessor()

    def start(self):
        app = QtWidgets.QApplication(sys.argv)
        self.ui = Ui_Dialog(self)
        self.ui.show()
        sys.exit(app.exec_())

    def load_files(self):
        selected_dir = QFileDialog.getExistingDirectory(None, caption='Choose Directory', directory=os.getcwd())
        self.file_paths = self.file_io.search(selected_dir)
        # self.file_paths = self.file_io.search("/Users/joonhyoungjeon/Downloads")
        self.ui.listView.clear()
        for filepath in self.file_paths:
            self.ui.listView.addItem(filepath[1])

    def add_files(self):
        selected_dir = QFileDialog.getExistingDirectory(None, caption='Choose Directory', directory=os.getcwd())
        searched_files = self.file_io.search(selected_dir)
        self.file_paths.extend(searched_files)
        for filepath in searched_files:
            self.ui.listView.addItem(filepath[1])

    def open_grabCut(self):
        plt.close()
        selected_index = self.ui.listView.currentRow()
        if selected_index == -1:
            self.ui.show_popup_ok("Warning", "한개의 이미지를 선택 후 동작해주세요")
            return
        selected_file_path = os.path.join(self.file_paths[selected_index][0], self.file_paths[selected_index][1])
        setting_controller = GrabCutController()
        setting_controller.start()
        setting_controller.loadImageFromFile(selected_file_path)

    def itemClicked(self):
        selected_index = self.ui.listView.currentRow()
        selected_file_path = os.path.join(self.file_paths[selected_index][0], self.file_paths[selected_index][1])
        selected_image = cv2.imread(selected_file_path)
        plt.close()
        plt.figure(num=self.ui.listView.currentItem().text(), figsize=(5, 3))
        plt.subplot(1, 1, 1)
        plt.axis('off')
        plt.tight_layout()
        plt.imshow(cv2.cvtColor(selected_image, cv2.COLOR_BGR2RGB))
        plt.show()

    def useGoogleApi(self):
        selected_index = self.ui.listView.currentRow()
        if selected_index == -1:
            self.ui.show_popup_ok("Warning", "한개의 이미지를 선택 후 동작해주세요")
            return

        plt.close('all')
        selected_file_path = os.path.join(self.file_paths[selected_index][0], self.file_paths[selected_index][1])
        objects = self.image_preprocessor.localize_objects(selected_file_path)
        bounding_box = self.image_preprocessor.get_boundingBox_google_api(selected_file_path, objects)
        setting_controller = GrabCutController(selected_file_path, bounding_box)
        setting_controller.start()
        setting_controller.show_selected_area()

if __name__ == '__main__':
    controller = Controller(".png")
    controller.start()
