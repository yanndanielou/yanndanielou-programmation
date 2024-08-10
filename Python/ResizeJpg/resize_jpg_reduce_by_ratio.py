import os

import sys
from PIL import Image
from pathlib import Path


args = sys.argv[1:]



print("args size" + str(len(args)))
print("args:")
print(str(args))

input_jpg_file_full_path=args[0]

size_ratio_to_apply_str=args[1]
size_ratio_to_apply_float = float(size_ratio_to_apply_str)
print("size_ratio_to_apply:" + str(size_ratio_to_apply_float))


input_jpg_file_path = Path(input_jpg_file_full_path)
print("input_jpg_file_path:" + str(input_jpg_file_path))

input_jpg_file_directory = os.path.dirname(input_jpg_file_path)
print("input_jpg_file_directory:" + input_jpg_file_directory)

input_jpg_file_name_with_extension = os.path.basename(input_jpg_file_path)
print("input_jpg_file_name_with_extension:" + input_jpg_file_name_with_extension)

input_jpg_file_name_with_extension = input_jpg_file_path.name
print("input_jpg_file_name_with_extension:" + input_jpg_file_name_with_extension)

input_jpg_file_name_without_extension = input_jpg_file_path.stem
print("input_jpg_file_name_without_extension:" + input_jpg_file_name_without_extension)

input_jpg_file_extension = input_jpg_file_path.suffix
print("input_jpg_file_extension:" + input_jpg_file_extension)

output_file_name_without_extension=str(input_jpg_file_name_without_extension) + "_ratio_" + str(size_ratio_to_apply_float).replace(".","_")
print("output_file_name_without_extension:" + output_file_name_without_extension)

output_file_name_with_extension=output_file_name_without_extension + str(input_jpg_file_extension)
print("output_file_name_with_extension:" + output_file_name_with_extension)


img = Image.open(input_jpg_file_full_path)
print("img.size:" + str(img.size))


input_image_width = img.size[0]
input_image_height = img.size[1]

print("input_image_width:" + str(input_image_width))
print("input_image_height:" + str(input_image_height))

output_image_width = int(input_image_width * size_ratio_to_apply_float)
output_image_height = int(input_image_height * size_ratio_to_apply_float)

print("output_image_width:" + str(output_image_width))
print("output_image_height:" + str(output_image_height))

img = img.resize((output_image_width, output_image_height), Image.LANCZOS)
img.save(output_file_name_with_extension)
