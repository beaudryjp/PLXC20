int x=7;
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
print(x+y);
f(x,y);
y = x*y+7;
print(x+y);

