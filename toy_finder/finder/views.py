# Create your views here.
import json
import sys

from django.http import HttpResponse
from rest_framework import views

sys.path.append("..")

from toy_finder.toyFinder import wsgi
from toy_finder.toyFinder import toy

from rest_framework.response import Response
from rest_framework.parsers import MultiPartParser, FormParser


class FileUpload(views.APIView):
    parser_classes = (MultiPartParser, FormParser, )

    def post(self, request):
        content = request.data["files"].read()
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

    def post(self, request, image_url):
        print("Post Call!")
        print(image_url)

