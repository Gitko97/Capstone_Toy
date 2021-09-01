import urllib.request

import cv2
from PyQt5.QtGui import *

from toy_finder.toy_preprocessor.image_utils.preprocessor import Image_PreProcessor
from toy_finder.toy_preprocessor.ui.grabcut_ui import Ui_GrabCut


class GrabCutController(object):
    def __init__(self,selected_file_path, bounding_box):
        self.image_preprocessor = Image_PreProcessor()
        self.ui = Ui_GrabCut(self)
        self.original_image = cv2.imread(selected_file_path, cv2.IMREAD_COLOR)
        self.bounding_box = bounding_box

    def start(self):
        self.ui.show()

    def loadImageFromWeb(self):
        # Web에서 Image 정보 로드
        urlString = "https://avatars1.githubusercontent.com/u/44885477?s=460&v=4"
        imageFromWeb = urllib.request.urlopen(urlString).read()

        # 웹에서 Load한 Image를 이용하여 QPixmap에 사진데이터를 Load하고, Label을 이용하여 화면에 표시
        self.qPixmapWebVar = QPixmap()
        self.qPixmapWebVar.loadFromData(imageFromWeb)
        self.qPixmapWebVar = self.qPixmapWebVar.scaledToWidth(600)
        self.lbl_picture.setPixmap(self.qPixmapWebVar)

    def saveImageFromWeb(self):
        # Label에서 표시하고 있는 사진 데이터를 QPixmap객체의 형태로 반환받은 후, save함수를 이용해 사진 저장
        self.qPixmapSaveVar = self.lbl_picture.pixmap()
        self.qPixmapSaveVar.save("SavedImage.jpg")

    def show_selected_area(self):
        image = self.image_preprocessor.show_selected_area(self.original_image, self.bounding_box)
        self.ui.loadImageFromNumpy(cv2.cvtColor(image, cv2.COLOR_BGR2RGB))

    def loadImageFromNumpy(self, image):
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        self.ui.loadImageFromFile(image)

    def loadImageFromFile(self, file_path):
        image = cv2.imread(file_path, cv2.IMREAD_COLOR)
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        self.ui.loadImageFromNumpy(image)
