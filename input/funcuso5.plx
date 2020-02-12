int h(int t) {
   return t/2;
}
int g(int z) {
   return h(z)*h(z);
}
int f(int x,int y) {
   return g(x+y);
}
print(f(6,g(h(8))));

