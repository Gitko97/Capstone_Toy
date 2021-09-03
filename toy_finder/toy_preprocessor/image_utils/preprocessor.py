import sys

import cv2
import numpy as np
from matplotlib import pyplot as plt


class Image_PreProcessor(object):
    def modify_grabCut(self,original_img, labels):
        label = cv2.GC_BGD
        lbl_clrs = {cv2.GC_BGD: (0, 0, 0), cv2.GC_FGD: (255, 255, 255)}
        height, _, _ = original_img.shape
        mouse_pressed = False
        select_mode = "Background"
        show_img = np.copy(original_img)
        show_img[(labels == cv2.GC_PR_BGD) | (labels == cv2.GC_BGD)] //= 3
        text_img = np.copy(show_img)

        def mouse_callback(event, x, y, flags, param):
            nonlocal mouse_pressed

            if event == cv2.EVENT_LBUTTONDOWN:
                mouse_pressed = True
                cv2.circle(labels, (x, y), 5, label, cv2.FILLED)
                cv2.circle(show_img, (x, y), 5, lbl_clrs[label], cv2.FILLED)

            elif event == cv2.EVENT_MOUSEMOVE:
                if mouse_pressed:
                    cv2.circle(labels, (x, y), 5, label, cv2.FILLED)
                    cv2.circle(show_img, (x, y), 5, lbl_clrs[label], cv2.FILLED)

            elif event == cv2.EVENT_LBUTTONUP:
                mouse_pressed = False

        # In[10]:

        cv2.namedWindow('image')
        cv2.setMouseCallback('image', mouse_callback)

        while True:
            if mouse_pressed:
                cv2.imshow('image', show_img)
            else:
                text_img = np.copy(show_img)
                cv2.putText(text_img, "Mode : {}".format(select_mode) , (0, height),
                            cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
                cv2.imshow('image', text_img)

            k = cv2.waitKey(1)

            if k == ord('a') and not mouse_pressed:
                show_img = original_img.copy()
                show_img[(labels == cv2.GC_PR_BGD) | (labels == cv2.GC_BGD)] //= 3

            if k == ord('s') and not mouse_pressed:
                break

            elif k == ord('l'):
                if select_mode is "Background":
                    select_mode = "Object"
                else:
                    select_mode = "Background"
                label = cv2.GC_FGD - label

        cv2.destroyAllWindows()
        return labels

    def grabCutting(self, img, point, labels=None):
        x = point["min_x"]
        y = point["min_y"]
        w = point["max_x"] - x
        h = point["max_y"] - y
        if labels is None:
            labels = np.zeros(img.shape[:2], np.uint8)

        labels, bgdModel, fgdModel = cv2.grabCut(img, labels, (x, y, w, h), None, None, 5, cv2.GC_INIT_WITH_RECT)
        return labels

    def rescale_image(self, image, scale=2):
        height, weight, c = image.shape
        img = cv2.resize(image, (height * scale, weight * scale), interpolation=cv2.INTER_AREA)
        return img

    def draw_bounding_box(self, img):
        height, weight, c = img.shape
        show_img = np.copy(img)
        mouse_pressed = False
        y = x = w = h = 0
        point = dict()
        def mouse_callback(event, _x, _y, flags, param):
            nonlocal show_img, x, y, w, h, mouse_pressed
            if event == cv2.EVENT_LBUTTONDOWN:
                mouse_pressed = True
                x, y = _x, _y
                show_img = np.copy(img)

            elif event == cv2.EVENT_MOUSEMOVE:
                if mouse_pressed:
                    show_img = np.copy(img)
                    cv2.rectangle(show_img, (x, y),
                                  (_x, _y), (0, 255, 0), 3)

            elif event == cv2.EVENT_LBUTTONUP:
                cv2.putText(show_img, "Press 'A' to close", (0, height),
                            cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
                mouse_pressed = False
                w, h = _x - x, _y - y

        cv2.namedWindow('image')
        cv2.setMouseCallback('image', mouse_callback)

        while True:
            cv2.imshow('image', show_img)
            k = cv2.waitKey(1)

            if k == ord('a') and not mouse_pressed:
                point["min_x"] = x
                point["min_y"] = y
                point["max_x"] = x+w
                point["max_y"] = y+h
                if w * h > 0:
                    break

        cv2.destroyAllWindows()
        return point

    def show_selected_area(self, image, bounding_box):
        image_pre = np.copy(image)
        image_pre = image_pre // 3
        min_x = bounding_box["min_x"]
        min_y = bounding_box["min_y"]
        max_x = bounding_box["max_x"]
        max_y = bounding_box["max_y"]
        image_pre[min_y:max_y, min_x:max_x, :] = image_pre[min_y:max_y, min_x:max_x, :] * 3
        return image_pre

    def get_boundingBox_with_object(self, image, object):
        point = dict()
        h, w, c = image.shape
        point["min_x"] = int(min([vertex.x for vertex in object.bounding_poly.normalized_vertices]) * w)
        point["min_y"] = int(min([vertex.y for vertex in object.bounding_poly.normalized_vertices]) * h)
        point["max_x"] = int(max([vertex.x for vertex in object.bounding_poly.normalized_vertices]) * w)
        point["max_y"] = int(max([vertex.y for vertex in object.bounding_poly.normalized_vertices]) * h)
        return point

    def get_cutted_image(self, image, bounding_box):
        image_pre = np.full_like(image, 255)
        min_x = bounding_box["min_x"]
        min_y = bounding_box["min_y"]
        max_x = bounding_box["max_x"]
        max_y = bounding_box["max_y"]

        image_pre[min_y:max_y, min_x:max_x, :] = image[min_y:max_y, min_x:max_x, :]

        return image_pre

    def get_boundingBox_google_api(self, file_path, objects):
        result = dict()
        result["button_clicked"] = False
        def on_press(event):
            sys.stdout.flush()
            result["object"] = objects[int(event.key) - 1]
            plt.close()
            result["button_clicked"] = True
            return

        image = cv2.imread(file_path, cv2.IMREAD_COLOR)
        h, w, c = image.shape
        for i, object_ in enumerate(objects):
            min_x = int(min([vertex.x for vertex in object_.bounding_poly.normalized_vertices]) * w)
            min_y = int(min([vertex.y for vertex in object_.bounding_poly.normalized_vertices]) * h)
            max_x = int(max([vertex.x for vertex in object_.bounding_poly.normalized_vertices]) * w)
            max_y = int(max([vertex.y for vertex in object_.bounding_poly.normalized_vertices]) * h)
            print(min_x, "/", min_y, "/", max_x, "/", max_y)
            cv2.putText(image, str(i + 1), (max_x, max_y), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
            cv2.rectangle(image, (min_x, min_y), (max_x, max_y), (0, 255, 0), 2)
        fig, ax = plt.subplots()
        fig.canvas.mpl_connect('key_press_event', on_press)
        plt.axis('off')
        plt.tight_layout()

        plt.imshow(cv2.cvtColor(image, cv2.COLOR_BGR2RGB))
        plt.show()
        while True:
            if result["button_clicked"]:
                plt.close()
                break
        return self.get_boundingBox_with_object(image, result["object"])

    def localize_objects(self, path):

        from google.cloud import vision
        client = vision.ImageAnnotatorClient()

        with open(path, 'rb') as image_file:
            content = image_file.read()
        image = vision.Image(content=content)

        objects = client.object_localization(
            image=image).localized_object_annotations

        # print('Number of objects found: {}'.format(len(objects)))
        # for object_ in objects:
        #     print('\n{} (confidence: {})'.format(object_.name, object_.score))
        #     print('Normalized bounding polygon vertices: ')
        #     for vertex in object_.bounding_poly.normalized_vertices:
        #         print(' - ({}, {})'.format(vertex.x, vertex.y))

        return objects

    def localize_objects_file(self, file):

        from google.cloud import vision
        client = vision.ImageAnnotatorClient()

        content = file
        image = vision.Image(content=content)

        objects = client.object_localization(
            image=image).localized_object_annotations

        return objects
