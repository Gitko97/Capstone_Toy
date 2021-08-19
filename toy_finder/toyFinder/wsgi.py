"""
WSGI config for toyFinder project.

It exposes the WSGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/3.2/howto/deployment/wsgi/
"""

import os

from django.core.wsgi import get_wsgi_application

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'toyFinder.settings')
application = get_wsgi_application()

from google.cloud import vision
from . import toy


google_api_key_pos = ""
storage_bucket_name = ""
storage_region = ""
product_setID = ""
product_category = ""
product_csv_url = ""

os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = google_api_key_pos

