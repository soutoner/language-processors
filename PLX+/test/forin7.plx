int a[3];
int b[3];
a[0] = 4;
a[1] = 5;
a[2] = 6;
b[0] = 11;
b[1] = 20;
b[2] = 21;
int x;
int y;
int z;
for x in a do {
   for y in b do {
      z = z + x*y;
   }
}
print(z);

