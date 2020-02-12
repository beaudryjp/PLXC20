int f(int x,int y,char z,char t) {
   print(t);
   print(z);
   return x-(int)y;
}
char g(char a, char b) {
   int x = (int) a;
   int y = (int) b;
   return (char) ((x+y)/2);
}
print(1);
char x;
x=g('A','C');
f(1, (int)x, x, 'Z');
print(x);
print(3);

