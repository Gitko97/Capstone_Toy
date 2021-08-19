# Create your views here.
import json
import sys

from django.http import HttpResponse
from rest_framework import views

sys.path.append("..")

from toy_finder.toyFinder import wsgi
from toy_finder.toyFinder import toy


class ToyFinderView(views.APIView):
    def get(self, request):
        image_url = request.GET["image_url"]
        scores = toy.get_similar_products_uri(wsgi.storage_bucket_name, wsgi.storage_region, wsgi.product_setID, wsgi.product_category, image_url, filter="")
        scores_json = json.dumps([score.__dict__ for score in scores])
        return HttpResponse(scores_json, content_type="text/json-comment-filtered")

    def post(self, request, image_url):
        print("Post Call!")
        print(image_url)

