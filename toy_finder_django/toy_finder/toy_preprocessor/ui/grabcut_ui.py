from PyQt5 import QtCore, QtGui, QtWidgets


class Ui_GrabCut(QtWidgets.QDialog):
    def __init__(self, controller):
        super().__init__()
        self.controller = controller
        self.setupUi()

    def closeEvent(self, event):
        self.close()

    def setupUi(self):
        self.setObjectName("Dialog")
        self.resize(659, 695)

        self.lbl_picture = QtWidgets.QLabel(self)
        self.lbl_picture.setGeometry(QtCore.QRect(20, 30, 600, 600))
        self.lbl_picture.setObjectName("lbl_picture")

        self.layoutWidget = QtWidgets.QWidget(self)
        self.layoutWidget.setGeometry(QtCore.QRect(20, 650, 631, 32))
        self.layoutWidget.setObjectName("layoutWidget")

        self.horizontalLayout = QtWidgets.QHBoxLayout(self.layoutWidget)
        self.horizontalLayout.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout.setObjectName("horizontalLayout")

        self.btn_change_box = QtWidgets.QPushButton(self.layoutWidget)
        self.btn_change_box.setObjectName("btn_change_box")
        self.horizontalLayout.addWidget(self.btn_change_box)
        self.btn_change_box.clicked.connect(self.controller.change_bounding_box)

        self.btn_change_grabcut = QtWidgets.QPushButton(self.layoutWidget)
        self.btn_change_grabcut.setObjectName("btn_change_grabcut")
        self.horizontalLayout.addWidget(self.btn_change_grabcut)
        self.btn_change_grabcut.clicked.connect(self.controller.change_grabCut)

        self.btn_savePicture = QtWidgets.QPushButton(self.layoutWidget)
        self.btn_savePicture.setObjectName("btn_savePicture")
        self.horizontalLayout.addWidget(self.btn_savePicture)
        self.btn_savePicture.clicked.connect(self.controller.saveImage)

        self.retranslateUi(self)
        QtCore.QMetaObject.connectSlotsByName(self)

    def retranslateUi(self, Dialog):
        _translate = QtCore.QCoreApplication.translate
        Dialog.setWindowTitle(_translate("Dialog", "Dialog"))
        self.lbl_picture.setText(_translate("Dialog", "TextLabel"))
        self.btn_change_box.setText(_translate("Dialog", "Change Box"))
        self.btn_change_grabcut.setText(_translate("Dialog", "Change GrabCut"))
        self.btn_savePicture.setText(_translate("Dialog", "Save"))

    def loadImageFromNumpy(self, image):
        # QPixmap 객체 생성 후 이미지 파일을 이용하여 QPixmap에 사진 데이터 Load하고, Label을 이용하여 화면에 표시
        image = QtGui.QImage(image, image.shape[1], \
                             image.shape[0], image.shape[1] * 3, QtGui.QImage.Format_RGB888)
        pix = QtGui.QPixmap(image)
        self.lbl_picture.setPixmap(pix.scaledToWidth(600))

