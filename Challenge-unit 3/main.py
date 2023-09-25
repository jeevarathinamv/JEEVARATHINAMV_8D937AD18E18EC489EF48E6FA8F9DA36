def linearsearchproduct(productList, targetproduct):
    indices = []

    for index, product in enumerate(productList):
        if product == targetproduct:
            indices.append(index)

    return indices

products = ["Laptops","cheese","sour cream","Laptops","KitKat","Laptops"]
target="Laptops"
target1 = "coca cola"
result = linearsearchproduct(products,target)
print(result)
