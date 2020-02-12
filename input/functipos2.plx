void f(int x) {
   print(x);
}
void g(char ch) {
   print(ch);
}
void h(int y,char z) {
   f(y);
   g(z);
}
g('A');
f(7);
h(9, 'B');

