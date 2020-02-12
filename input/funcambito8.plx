int x=2;
int y=7;
void f(int x, int y) {
   if (x==y) {
      y = y+2;
      if (x<y) {
         int y;
         x = y = x+y+3;
         print(x+y);
      }
      x = x*y+4;
     print(x);
  }
}
void g(int x) {
   int y=4;
   print(x+y);
}
print(y-x);
f(x+y,x+y);
print(y-x);
g(x);
print(y-x);
y = x*y+7;
print(y-x);

