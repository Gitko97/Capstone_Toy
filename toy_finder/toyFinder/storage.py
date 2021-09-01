from google.cloud import storage
from datetime import date
def check_cloud_storage(bucket_name):
    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)
    blobs = bucket.list_blobs()
    return blobs

def upload_blob(bucket_name, source_file, destination_blob_name):
    """Uploads a file to the bucket."""
    # The ID of your GCS bucket
    # bucket_name = "your-bucket-name"
    # The path to your file to upload
    # The ID of your GCS object
    # destination_blob_name = "storage-object-name"

    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(destination_blob_name)

    # blob.upload_from_filename(source_file_name)
    blob.upload_from_file(source_file)
    print(
        "File {} uploaded to {}.".format(
            source_file, destination_blob_name
        )
    )


def update_txt(bucket_name ,file_name, character, category, product_set, product_category):
    import os.path
    scriptpath = os.path.dirname(__file__)
    file = f"image_data/{date.today()}.txt"
    file_pos = os.path.join(scriptpath, file)
    os.makedirs(os.path.dirname(file_pos), exist_ok=True)

    file_url = f"gs://{bucket_name}/{file_name}.png"

    input_string = f"{file_url},,{product_set},,{product_category},,\"character={character},category={category}\"\n"
    with open(file_pos, 'a') as f:
        f.write(input_string)