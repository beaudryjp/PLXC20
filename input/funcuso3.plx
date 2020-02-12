int g(int z) {
   return z*z;
}
int f(int x,int y) {
   return g(x+y);
}
print(f(1,g(3)));

