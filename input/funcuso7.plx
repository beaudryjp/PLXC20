int a = 2;
int b = 3;
int f(int x,int y,int z) {
  return z-x+y;
}
int g(int z) {
   print(z);
   a = a+z;
   return z*z;
}
a = f(a, b, g(3)) + g(b);
print(a);
print(b);

