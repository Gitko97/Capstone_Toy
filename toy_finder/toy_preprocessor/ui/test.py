#!/usr/bin/env python
# coding: utf-8

# In[1]:


import cv2
import numpy as np
from toy_finder.toy_preprocessor.image_utils.preprocessor import Image_PreProcessor

# In[2]:


img = cv2.imread('/Users/joonhyoungjeon/Downloads/gym_mac_1.png', cv2.IMREAD_COLOR)
# image_util = Image_PreProcessor()
# image_util.draw_bounding_box(img)


height, weight, c = img.shape
scale =2
img = cv2.resize(img, (height * scale, weight * scale), interpolation=cv2.INTER_AREA)
show_img = np.copy(img)
mouse_pressed = False
y = x = w = h = 0

def mouse_callback(event, _x, _y, flags, param):
    global show_img, x, y, w, h, mouse_pressed
    if event == cv2.EVENT_LBUTTONDOWN:
        print("Mouse Press!")
        mouse_pressed = True
        x, y = _x, _y
        show_img = np.copy(img)
        cv2.putText(show_img, 'Mouse Press!', (0, height*2),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)

    elif event == cv2.EVENT_MOUSEMOVE:
        if mouse_pressed:
            show_img = np.copy(img)
            cv2.rectangle(show_img, (x, y),
                          (_x, _y), (0, 255, 0), 3)

    elif event == cv2.EVENT_LBUTTONUP:
        print("Mouse Down!")
        mouse_pressed = False
        w, h = _x - x, _y - y

# In[5]:

cv2.namedWindow('image')
cv2.setMouseCallback('image', mouse_callback)

while True:
    cv2.imshow('image', show_img)
    k = cv2.waitKey(1)

    if k == ord('a') and not mouse_pressed:
        print("종료")
        if w * h > 0:
            break

cv2.destroyAllWindows()
# In[12]:


labels = np.zeros(img.shape[:2],np.uint8)

labels, bgdModel, fgdModel = cv2.grabCut(img, labels, (x, y, w, h), None, None, 5, cv2.GC_INIT_WITH_RECT)
print(np.shape(labels))
show_img = np.copy(img)
show_img[(labels == cv2.GC_PR_BGD)|(labels == cv2.GC_BGD)] //= 3

cv2.imshow('image', show_img)
cv2.waitKey()
cv2.destroyAllWindows()


# In[8]:


label = cv2.GC_BGD
lbl_clrs = {cv2.GC_BGD: (0,0,0), cv2.GC_FGD: (255,255,255)}

def mouse_callback(event, x, y, flags, param):
    global mouse_pressed

    if event == cv2.EVENT_LBUTTONDOWN:
        print("Mouse Down!")
        mouse_pressed = True
        cv2.circle(labels, (x, y), 5, label, cv2.FILLED)
        cv2.circle(show_img, (x, y), 5, lbl_clrs[label], cv2.FILLED)

    elif event == cv2.EVENT_MOUSEMOVE:
        if mouse_pressed:
            cv2.circle(labels, (x, y), 5, label, cv2.FILLED)
            cv2.circle(show_img, (x, y), 5, lbl_clrs[label], cv2.FILLED)

    elif event == cv2.EVENT_LBUTTONUP:
        print("Mouse Press!")
        mouse_pressed = False


# In[10]:


cv2.namedWindow('image')
cv2.setMouseCallback('image', mouse_callback)

while True:
    cv2.imshow('image', show_img)
    k = cv2.waitKey(1)

    if k == ord('a') and not mouse_pressed:
        print("종료")
        break
    elif k == ord('l'):
        print("체인지")
        label = cv2.GC_FGD - label

cv2.destroyAllWindows()

labels, bgdModel, fgdModel = cv2.grabCut(img, labels, None, bgdModel, fgdModel, 5, cv2.GC_INIT_WITH_MASK)

show_img = np.copy(img)
show_img[(labels == cv2.GC_PR_BGD)|(labels == cv2.GC_BGD)] //= 3

cv2.imshow('image', show_img)
cv2.waitKey()
cv2.destroyAllWindows()


# In[ ]:




