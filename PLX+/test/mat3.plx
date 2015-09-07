int i;
int a[10];
int suma;
suma=0;
for(i=0; i<10; i=i+1) {
    a[i] = i;
    suma = suma + a[i]*a[i];   
}
print(suma);
