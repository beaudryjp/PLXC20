int f(int x);
int f(int x) {
   int y = g(g(x));
   return y;
}
int g(int z) {
   print(2*z);
   return 2*z;
}
f(f(7)); 

