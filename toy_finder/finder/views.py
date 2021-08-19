# Create your views here.
from django.http import HttpResponse
from rest_framework import views
import sys
sys.path.append("..")

from toy_finder.toyFinder import wsgi
from toy_finder.toyFinder import toy

class ToyFinderView(views.APIView):
    def get(self, request):
        print(request.GET["image_url"])
        image_url = request.GET["image_url"]
        print("Get Call!")
        # print(image_url)
        scores = toy.get_similar_products_uri(wsgi.storage_bucket_name, wsgi.storage_region, wsgi.product_setID, wsgi.product_category, image_url, filter="")
        return HttpResponse("성공 : " + "image_url")

    def post(self, request, image_url):
        print("Post Call!")
        print(image_url)

