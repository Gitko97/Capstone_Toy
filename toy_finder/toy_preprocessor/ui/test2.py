import base64
import io

import requests
import cv2
import numpy as np
from PIL import Image

url = "http://127.0.0.1:8000/finder/api/upload_file/bounding_box/"

payload={}
files=[
  ('file',('alphabet_2.png',open('/Users/joonhyoungjeon/Downloads/alphabet_1.png','rb'),'image/png'))
]
headers = {}

response = requests.request("GET", url, headers=headers, data=payload, files=files)
#####

content = response.content
result_json = response.json()

image = result_json["file"]

string_byte = image
print(type(string_byte))

image_byte = string_byte.encode("utf-8")
print(type(image_byte))

print(string_byte)