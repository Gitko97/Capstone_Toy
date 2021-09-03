from django.urls import path
from . import views

urlpatterns = [
    path('api/image_url/', views.ToyFinderView.as_view(), name='image_url'),
    path('api/upload_file/', views.FileUpload.as_view(), name='upload_url'),
    path('api/storage/', views.StorageView.as_view(), name='storage'),
    path('api/upload_file/bounding_box/', views.GoogleApiView.as_view(), name='bounding_box'),
]