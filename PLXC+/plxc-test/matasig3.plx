int a[3];
int b[2];
int suma;
a = {1,2,3};
b = a;
int i;
for(i=0; i<3; i=i+1) {
    suma = suma + b[i];
}
print(suma);

