# Create your views here.

from rest_framework import views
from ..toy_finder import wsgi
from ..toy_finder import toy

class ToyFinderView(views.APIView):
    def get(self, request, image_url):
        print("Get Call!")
        print(image_url)

    def post(self, request, image_url):
        print("Post Call!")
        print(image_url)

