# Create your views here.
import base64
import json
import sys

import numpy as np
from django.http import HttpResponse
from rest_framework import views

sys.path.append("..")
import cv2
from toy_finder.toyFinder import wsgi
from toy_finder.toyFinder import toy
from toy_finder.toyFinder import storage
from toy_finder.toy_preprocessor.image_utils.preprocessor import Image_PreProcessor
from rest_framework.parsers import MultiPartParser, FormParser


class FileUpload(views.APIView):
    parser_classes = (MultiPartParser, FormParser, )

    def get(self, request):
        content = request.data["file"].read()
        scores = toy.get_similar_products_file(wsgi.storage_bucket_name, wsgi.storage_region, wsgi.product_setID,
                                              wsgi.product_category, content, filter="")
        scores_json = json.dumps([score.__dict__ for score in scores])
        return HttpResponse(scores_json, content_type="text/json-comment-filtered")

class ToyFinderView(views.APIView):
    def get(self, request):
        image_url = request.GET["url"]
        scores = toy.get_similar_products_uri(wsgi.storage_bucket_name, wsgi.storage_region, wsgi.product_setID, wsgi.product_category, image_url, filter="")
        scores_json = json.dumps([score.__dict__ for score in scores])
        return HttpResponse(scores_json, content_type="text/json-comment-filtered")

class GoogleApiView(views.APIView):
    def makeBoundingBoxResponse(self, content):
        image_preprocessor = Image_PreProcessor()
        byte_image = content.read()
        decoded = cv2.imdecode(np.fromstring(byte_image, np.uint8), cv2.IMREAD_UNCHANGED)
        objects = image_preprocessor.localize_objects_file(byte_image)

        # points_list = list()
        response_body = dict()
        response_body["box_num"] = len(objects)
        for i, object_ in enumerate(objects):
            point = image_preprocessor.get_boundingBox_with_object(decoded, object_)
            min_x = point["min_x"]
            min_y = point["min_y"]
            max_x = point["max_x"]
            max_y = point["max_y"]
            # points_list.append(point)
            cv2.putText(decoded, str(i + 1), (max_x, max_y), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
            cv2.rectangle(decoded, (min_x, min_y), (max_x, max_y), (0, 255, 0), 2)
            response_body["box_"+str(i+1)] = point

        res, im_png = cv2.imencode(".png", decoded)
        base64_bytes = base64.b64encode(im_png.tobytes())
        base64_string = base64_bytes.decode("utf-8")
        response_body["file"] = base64_string

        response = HttpResponse(json.dumps(response_body), content_type="text/json-comment-filtered")
        return response

    def get(self, request):
        content = request.data["file"]
        response = self.makeBoundingBoxResponse(content)
        return response

class StorageView(views.APIView):
    def get(self, request):
        bucket_name = request.GET["bucket_name"]
        blob_list = storage.check_cloud_storage(bucket_name)
        blobs_json = json.dumps([blob.name for blob in blob_list])
        return HttpResponse(blobs_json, content_type="text/json-comment-filtered")

    def post(self, request):
        bucket_name = request.POST["bucket_name"]
        file_name = request.data["file_name"]
        content = request.data["file"]
        # ToDo: 파일 이름 중복 처리 필요(현재는 덮어쓰기)
        storage.upload_blob(bucket_name, content, f"{file_name}.png")


        # content = request.data["file_name"]
        return HttpResponse("file_path", content_type="text/json-comment-filtered")