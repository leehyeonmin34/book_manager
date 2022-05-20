function mapcar(f, lst1, lst2) {
    result = [];
    i = 0;
    return lst1.map((x) => {
        val = f(x, lst2[i]);
        i++;
        return val;
    });
}

function innerProduct(lst1, lst2) {
    return mapcar((x, y) => x * y, lst1, lst2).reduce(
        (acc, curr, i) => acc + curr
    );
}
