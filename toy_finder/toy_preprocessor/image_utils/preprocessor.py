import sys

import cv2
import numpy as np
from matplotlib import pyplot as plt


class Image_PreProcessor(object):


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

    def show_cutted_image(self, image, bounding_box):
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
        """Localize objects in the local image.

        Args:
        path: The path to the local file.
        """

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
        """Localize objects in the local image.

        Args:
        path: The path to the local file.
        """

        from google.cloud import vision
        client = vision.ImageAnnotatorClient()

        content = file
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
