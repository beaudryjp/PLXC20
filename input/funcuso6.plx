int a = 2;
int f(int x,int y) {
   a = a+x+y;
   print(a);
   return x+y;
}
int g(int z) {
   print(z);
   a = a+z;
   return z*z;
}
print(f(g(a),g(a)));
print(a);


