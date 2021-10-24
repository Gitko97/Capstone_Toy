# Imports the Google Cloud client library

from google.cloud import vision

from toy_finder.finder.models import ToyResult





def import_product_sets(project_id, location, gcs_uri):
    """Import images of different products in the product set.
    Args:
        project_id: Id of the project.
        location: A compute region name.
        gcs_uri: Google Cloud Storage URI.
            Target files must be in Product Search CSV format.
    """
    client = vision.ProductSearchClient()

    # A resource that represents Google Cloud Platform location.
    location_path = f"projects/{project_id}/locations/{location}"

    # Set the input configuration along with Google Cloud Storage URI
    gcs_source = vision.ImportProductSetsGcsSource(
        csv_file_uri=gcs_uri)
    input_config = vision.ImportProductSetsInputConfig(
        gcs_source=gcs_source)

    # Import the product sets from the input URI.
    response = client.import_product_sets(
        parent=location_path, input_config=input_config)

    print('Processing operation name: {}'.format(response.operation.name))
    # synchronous check of operation status
    result = response.result()
    print('Processing done.')

    for i, status in enumerate(result.statuses):
        print('Status of processing line {} of the csv: {}'.format(
            i, status))
        # Check the status of reference image
        # `0` is the code for OK in google.rpc.Code.
        if status.code == 0:
            print(i)
            # reference_image = result.reference_images[i]
            # print(reference_image)
        else:
            print('Status code not OK: {}'.format(status.message))

def purge_products_in_product_set(
        project_id, location, product_set_id, force):
    """Delete all products in a product set.
    Args:
        project_id: Id of the project.
        location: A compute region name.
        product_set_id: Id of the product set.
        force: Perform the purge only when force is set to True.
    """
    client = vision.ProductSearchClient()

    parent = f"projects/{project_id}/locations/{location}"

    product_set_purge_config = vision.ProductSetPurgeConfig(
        product_set_id=product_set_id)

    # The purge operation is async.
    operation = client.purge_products(request={
        "parent": parent,
        "product_set_purge_config": product_set_purge_config,
        # The operation is irreversible and removes multiple products.
        # The user is required to pass in force=True to actually perform the
        # purge.
        # If force is not set to True, the service raises an exception.
        "force": force
    })

    operation.result(timeout=500)

    print('Deleted products in product set.')

def delete_product_sets(project_id, location, product_id):
    client = vision.ProductSearchClient()

    location_path = f"projects/{project_id}/locations/{location}/productSets/{product_id}"
    client.delete_product_set(name=location_path)

    print(f"{product_id} delete success!")

def list_products_in_product_set(project_id, location, product_id):
    client = vision.ProductSearchClient()

    # A resource that represents Google Cloud Platform location.
    location_path = f"projects/{project_id}/locations/{location}/productSets/{product_id}"
    products = client.list_products_in_product_set(name=location_path)

    print("Products list in "+ product_id)
    for product in products:
        print(product)



def list_product_sets(project_id, location):
    """List all product sets.
    Args:
        project_id: Id of the project.
        location: A compute region name.
    """
    client = vision.ProductSearchClient()

    # A resource that represents Google Cloud Platform location.
    location_path = f"projects/{project_id}/locations/{location}"

    # List all the product sets available in the region.
    product_sets = client.list_product_sets(parent=location_path)

    # Display the product set information.
    for product_set in product_sets:
        print('Product set name: {}'.format(product_set.name))
        print('Product set id: {}'.format(product_set.name.split('/')[-1]))
        print('Product set display name: {}'.format(product_set.display_name))
        print('Product set index time: ')
        print(product_set.index_time)


def get_similar_products_file(
        project_id, location, product_set_id, product_category,
        file_content, filter):
    """Search similar products to image.
    Args:
        project_id: Id of the project.
        location: A compute region name.
        product_set_id: Id of the product set.
        product_category: Category of the product.
        file_path: Local file path of the image to be searched.
        filter: Condition to be applied on the labels.
        Example for filter: (color = red OR color = blue) AND style = kids
        It will search on all products with the following labels:
        color:red AND style:kids
        color:blue AND style:kids
    """
    # product_search_client is needed only for its helper methods.
    product_search_client = vision.ProductSearchClient()
    image_annotator_client = vision.ImageAnnotatorClient()

    # Read the image as a stream of bytes.
    # with open(file_path, 'rb') as image_file:
    #     content = image_file.read()
    #
    # print(content)
    # print(type(content))
    # Create annotate image request along with product search feature.
    image = vision.Image(content=file_content)

    # product search specific parameters
    product_set_path = product_search_client.product_set_path(
        project=project_id, location=location,
        product_set=product_set_id)
    product_search_params = vision.ProductSearchParams(
        product_set=product_set_path,
        product_categories=[product_category],
        filter=filter)
    image_context = vision.ImageContext(
        product_search_params=product_search_params)

    # Search products similar to the image.
    response = image_annotator_client.product_search(
        image, image_context=image_context, max_results=5)

    index_time = response.product_search_results.index_time
    results = response.product_search_results.results

    top_three = []
    for i in range(3):
        product = results[i].product
        toy = ToyResult(index_time, results[i].score, results[i].image, product.name, product.product_labels)
        top_three.append(toy)
    return top_three


def get_similar_products_uri(
        project_id, location, product_set_id, product_category,
        image_uri, filter):
    """Search similar products to image.
    Args:
        project_id: Id of the project.
        location: A compute region name.
        product_set_id: Id of the product set.
        product_category: Category of the product.
        image_uri: Cloud Storage location of image to be searched.
        filter: Condition to be applied on the labels.
        Example for filter: (color = red OR color = blue) AND style = kids
        It will search on all products with the following labels:
        color:red AND style:kids
        color:blue AND style:kids
    """
    # product_search_client is needed only for its helper methods.
    product_search_client = vision.ProductSearchClient()
    image_annotator_client = vision.ImageAnnotatorClient()

    # Create annotate image request along with product search feature.
    image_source = vision.ImageSource(image_uri=image_uri)
    image = vision.Image(source=image_source)

    # product search specific parameters
    product_set_path = product_search_client.product_set_path(
        project=project_id, location=location,
        product_set=product_set_id)
    product_search_params = vision.ProductSearchParams(
        product_set=product_set_path,
        product_categories=[product_category],
        filter=filter)
    image_context = vision.ImageContext(
        product_search_params=product_search_params)

    # Search products similar to the image.
    response = image_annotator_client.product_search(
        image, image_context=image_context)

    index_time = response.product_search_results.index_time
    results = response.product_search_results.results

    top_three = []
    for i in range(3):
        product = results[i].product
        toy = ToyResult(index_time, results[i].score, results[i].image, product.name, product.product_labels)
        top_three.append(toy)
    return top_three

def list_reference_images(
        project_id, location, product_id):
    """List all images in a product.
    Args:
        project_id: Id of the project.
        location: A compute region name.
        product_id: Id of the product.
    """
    client = vision.ProductSearchClient()

    # Get the full path of the product.
    product_path = client.product_path(
        project=project_id, location=location, product=product_id)

    # List all the reference images available in the product.
    reference_images = client.list_reference_images(parent=product_path)

    # Display the reference image information.
    for image in reference_images:
        print('Reference image name: {}'.format(image.name))
        print('Reference image id: {}'.format(image.name.split('/')[-1]))
        print('Reference image uri: {}'.format(image.uri))
        print('Reference image bounding polygons: {}'.format(
            image.bounding_polys))

