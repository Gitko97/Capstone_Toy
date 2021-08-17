from django.urls import path

from . import views

urlpatterns = [
    path('<str:image_url>', views.ToyFinderView.as_view(), name='finder'),
]