int x=1;
int y=3;
void f(int x, int y) {
   if (x==y) {
      int x=3;
      print(x);
   }
   print(x);
}
print(x);
f(x+y,y+x);
print(x);

