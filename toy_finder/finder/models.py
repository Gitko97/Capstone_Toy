from django.db import models

class ToyResult(models.Model):
    '''
    Attributes:
        name: The name of the algorithm.
        description: The short description of how the algorithm works.
        version: The version of the algorithm similar to software versioning.
        created_at: The date when MLAlgorithm was added.
        parent_endpoint: The reference to the Endpoint.
    '''
    score = models.CharField(max_length=128)
    image_name = models.CharField(max_length=100)
    product_name = models.CharField(max_length=100)
    product_label = models.CharField(max_length=100)

    @classmethod
    def create(cls, score, image_name, product_name, product_label):
        toy = cls(score=score, image_name=image_name, product_name=product_name, product_label=product_label)
        return toy

    def __str__(self):
        return "Score : {} / Image_name : {} / Product_name : {} / Product_label {}".format(self.score, self.image_name, self.product_name, self.product_label)
