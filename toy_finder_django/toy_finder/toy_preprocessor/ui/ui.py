# -*- coding: utf-8 -*-

from PyQt5 import QtCore, QtWidgets
from PyQt5.QtWidgets import QMessageBox


class Ui_Dialog(QtWidgets.QDialog):
    def __init__(self, controller):
        super().__init__()
        self.controller = controller
        self.setupUi()

    def show_popup_ok(self, title: str, content: str):
        msg = QMessageBox()
        msg.setWindowTitle(title)
        msg.setText(content)
        msg.setStandardButtons(QMessageBox.Ok)
        msg.exec_()

    def setupUi(self):
        self.setObjectName("Dialog")
        self.resize(550, 600)
        self.horizontalLayout = QtWidgets.QHBoxLayout()
        self.horizontalLayout.setObjectName("horizontalLayout")
        self.frame = QtWidgets.QFrame(self)
        self.frame.setFrameShape(QtWidgets.QFrame.StyledPanel)
        self.frame.setFrameShadow(QtWidgets.QFrame.Raised)
        self.frame.setObjectName("frame")

        self.listView = QtWidgets.QListWidget(self.frame)
        self.listView.setSelectionMode(QtWidgets.QAbstractItemView.ExtendedSelection)
        self.listView.setGeometry(QtCore.QRect(0, 0, 510, 431))
        self.listView.setObjectName("listView")
        self.listView.itemClicked.connect(self.controller.itemClicked)

        self.choose_directory_button = QtWidgets.QPushButton(self.frame)
        self.choose_directory_button.setGeometry(QtCore.QRect(30, 460, 201, 32))
        self.choose_directory_button.setObjectName("choose_directory_button")
        self.choose_directory_button.clicked.connect(self.controller.load_files)

        self.grabCut_button = QtWidgets.QPushButton(self.frame)
        self.grabCut_button.setGeometry(QtCore.QRect(30, 510, 201, 32))
        self.grabCut_button.setObjectName("grabCut_button")
        self.grabCut_button.clicked.connect(self.controller.open_grabCut)

        self.add_file_button = QtWidgets.QPushButton(self.frame)
        self.add_file_button.setGeometry(QtCore.QRect(260, 460, 201, 32))
        self.add_file_button.setObjectName("add_file_button")
        self.add_file_button.clicked.connect(self.controller.add_files)

        self.google_api_button = QtWidgets.QPushButton(self.frame)
        self.google_api_button.setGeometry(QtCore.QRect(260, 510, 201, 32))
        self.google_api_button.setObjectName("google_api_button")
        self.google_api_button.clicked.connect(self.controller.useGoogleApi)

        # self.listView.itemDoubleClicked.connect(self.controller.itemDoubleClicked)
        # self.listView.currentItemChanged.connect(self.chkCurrentItemChanged)

        self.horizontalLayout.addWidget(self.frame)
        self.setLayout(self.horizontalLayout)
        self.retranslateUi(self)

    def retranslateUi(self, Dialog):
        _translate = QtCore.QCoreApplication.translate
        Dialog.setWindowTitle(_translate("Dialog", "Dialog"))
        self.choose_directory_button.setText(_translate("Dialog", "폴더 선택"))
        self.grabCut_button.setText(_translate("Dialog", "GrabCut 사용"))
        self.add_file_button.setText(_translate("Dialog", "파일 추가"))
        self.google_api_button.setText(_translate("Dialog", "구글 API 사용"))