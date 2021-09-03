import cv2
import numpy as np
import os


from toy_finder.toy_preprocessor.image_utils.preprocessor import Image_PreProcessor
from toy_finder.toy_preprocessor.ui.grabcut_ui import Ui_GrabCut


class GrabCutController(object):
    def __init__(self,selected_file_path,original_img, bounding_box, grabcut_labels=None):
        self.image_preprocessor = Image_PreProcessor()
        self.ui = Ui_GrabCut(self)
        self.file_path, self.file_name = os.path.split(selected_file_path)
        self.original_image = original_img
        self.bounding_box = bounding_box
        self.grabcut_labels = grabcut_labels

    def start(self):
        self.ui.show()

    # def loadImageFromWeb(self):
    #     # Web에서 Image 정보 로드
    #     urlString = "https://avatars1.githubusercontent.com/u/44885477?s=460&v=4"
    #     imageFromWeb = urllib.request.urlopen(urlString).read()
    #
    #     # 웹에서 Load한 Image를 이용하여 QPixmap에 사진데이터를 Load하고, Label을 이용하여 화면에 표시
    #     self.qPixmapWebVar = QPixmap()
    #     self.qPixmapWebVar.loadFromData(imageFromWeb)
    #     self.qPixmapWebVar = self.qPixmapWebVar.scaledToWidth(600)
    #     self.lbl  _picture.setPixmap(self.qPixmapWebVar)

    def change_bounding_box(self):
        if self.grabcut_labels is None:
            self.original_image = self.image_preprocessor.rescale_image(self.original_image, 2)
        self.bounding_box = self.image_preprocessor.draw_bounding_box(self.original_image)
        if self.grabcut_labels is not None:
            self.grabcut_labels = self.image_preprocessor.grabCutting(self.original_image , self.bounding_box)
            self.show_img_with_grabcut_label()
            return
        self.show_img_with_bounding_box()

    def change_grabCut(self):
        if self.grabcut_labels is None:
            self.original_image = self.image_preprocessor.rescale_image(self.original_image, 2)
            labels = self.image_preprocessor.grabCutting(self.original_image, self.bounding_box)
        else:
            labels = self.grabcut_labels
        self.grabcut_labels = self.image_preprocessor.modify_grabCut(self.original_image, labels)
        self.show_img_with_grabcut_label()

    def saveImage(self):
        # Label에서 표시하고 있는 사진 데이터를 QPixmap객체의 형태로 반환받은 후, save함수를 이용해 사진 저장
        # qPixmapSaveVar = self.ui.lbl_picture.pixmap()
        # qPixmapSaveVar.save(save_file_path)
        save_file_path = os.path.join(self.file_path, "preprocess_"+self.file_name)
        if self.grabcut_labels is None:
            save_image = self.image_preprocessor.get_cutted_image(self.original_image, self.bounding_box)
            cv2.imwrite(save_file_path, save_image)
        else:
            save_image = np.copy(self.original_image)
            save_image[(self.grabcut_labels == cv2.GC_PR_BGD) | (self.grabcut_labels == cv2.GC_BGD)] = 255
            cv2.imwrite(save_file_path, save_image)

    def show_img_with_grabcut_label(self):
        if self.grabcut_labels is None:
            self.ui.show_popup_ok("Warning", "한개의 이미지를 선택 후 동작해주세요")
            return
        changed_img = np.copy(self.original_image)
        changed_img[(self.grabcut_labels== cv2.GC_PR_BGD) | (self.grabcut_labels == cv2.GC_BGD)] //= 3
        image = cv2.cvtColor(changed_img, cv2.COLOR_BGR2RGB)
        self.ui.loadImageFromNumpy(image)

    def show_img_with_bounding_box(self):
        image = self.image_preprocessor.show_selected_area(self.original_image, self.bounding_box)
        self.ui.loadImageFromNumpy(cv2.cvtColor(image, cv2.COLOR_BGR2RGB))

    def loadImageFromNumpy(self, image):
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        self.ui.loadImageFromNumpy(image)
