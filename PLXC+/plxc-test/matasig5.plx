int a[3];
int b[3];
int c[3];
int p;
c = b = a = {1,2,3};
int i;
for(i=0; i<3; i=i+1) {
    p = p+ a[i]*b[i]+c[i];
}
print(p);

