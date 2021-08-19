class ToyResult():
    '''
    Attributes:
        name: The name of the algorithm.
        description: The short description of how the algorithm works.
        version: The version of the algorithm similar to software versioning.
        created_at: The date when MLAlgorithm was added.
        parent_endpoint: The reference to the Endpoint.
    '''


    def __init__(self, score, image_name, product_name, product_labels):
        self.score = score
        self.image_name = str(image_name).split("/")[-1]
        self.product_name = str(product_name).split("/")[-1]
        self.product_label = self.make_label_to_json_string(product_labels)

    def make_label_to_json_string(self, product_labels):
        result_json = dict()
        for product_label in product_labels:
            key = product_label.__dict__['_pb'].key
            value = product_label.__dict__['_pb'].value
            result_json[key] = value
        return result_json

    def __str__(self):
        return "Score : {} / Image_name : {} / Product_name : {} / Product_label {}".format(self.score, self.image_name, self.product_name, self.product_label)
