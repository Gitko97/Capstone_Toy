from django.urls import path
from . import views

urlpatterns = [
    path('api/', views.ToyFinderView.as_view(), name='image_url'),
]