int a[3];
int b[5];
int suma;
a = {1,2,3};
b = a;
b[3] = 4;
b[4] = 5;
int i;
for(i=0; i<5; i=i+1) {
    suma = suma + b[i];
}
print(suma);

