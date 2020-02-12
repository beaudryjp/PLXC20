int* refer(int **x) {
   int y = 4 * **x;
   **x = y + 4;
   return &y;
}
int *a;
int *b;
*a = 4;
b = refer(&a);
print(*a);
print(*b);

