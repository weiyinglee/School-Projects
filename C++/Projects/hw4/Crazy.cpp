// Shun Lu and WeiYing Lee
// Homework 4
// Completed 10/27/2015

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <iomanip>
using namespace std;

void compileOutPut(vector<char>);
void executeOutPut(vector<char> symbol, int*& dp, int position, int count, int& direction);
void interactionMode(int* tape, int*& dp, int& count);

int main(int argc, char *argv[]){
   const int SIZE = 1000;
   int tape[SIZE] = {0};
   int *dp = tape;
   int direction = 0;  // This tracks pointer's position

   if (argc <= 1){   // Check if Null Pointer
      interactionMode(tape, dp, direction);
   }
   else{
      string arg = argv[1];
      string stringFile = argv[2];
      vector<char> symbol;

      if (arg == "-c"){ 
         ifstream fin(stringFile.c_str());
         if(fin.fail()){
            //Fail to read file
            cout << "Fail" << endl;
         }else{
            //Success read file
            char c;

            while(fin >> c){
               symbol.push_back(c);
            }
            compileOutPut(symbol);
         }
      }else if (arg == "-e"){
         ifstream fin(stringFile.c_str());
         if (fin.fail()){
            //Fail to read file
            cout << "Fail" << endl;
         }else{
            //Success read file
            char c;
            while (fin >> c){
               symbol.push_back(c);
            }
            executeOutPut(symbol, dp, 0, 0, direction);
         }
      }else{
         cout << "Argument not found." << endl;
      }
   }
   return 0;
}

void compileOutPut(vector<char> symbol){
   cout << "#include <iostream>\n" << endl;
   cout << "const int SIZE = 1000;" << endl;
   cout << "int tape[SIZE] = {0};" << endl;
   cout << "int *dp = tape;\n" << endl;
   cout << "int main() {" << endl;

   int count = 4;

   for (size_t i = 0; i < symbol.size(); i++)
   {
      switch (symbol[i])
      {
      case '+':
         cout << setw(count) << "" << "++*dp;" << endl; 
         break;
      case '-':
         cout << setw(count) << "" << "--*dp;" << endl;
         break;
      case '>':
         cout << setw(count) << "" << "++dp;" << endl;
         break;
      case '<':
         cout << setw(count) << "" << "--dp;" << endl;
         break;
      case ':':
         cout << setw(count) << "" << "std::cout << *dp;" << endl;
         break;
      case '.':
         cout << setw(count) << "" << "std::cout << ((char) *dp);" << endl;
         break;
      case '{':
         cout << setw(count) << "" << "while (*dp) {" << endl;
         count += 4;
         break;
      case '}':
         cout << setw(count - 4) << "" << "}" << endl;
         count -= 4;
         break;
      default:
         break;
      }
   }
   cout << "    return 0;" << endl;
   cout << "}" << endl;
}

void executeOutPut(vector<char> symbol, int*& dp, int position, int count, int& direction){
   int temp = 0;

   while (position < symbol.size())
   {
      switch (symbol[position])
      {
      case '+':
         ++*dp;
         position++;
         break;
      case '-':
         --*dp;
         position++;
         break;
      case '>':
         ++dp;
         position++;
         direction++;
         break;
      case '<':
         --dp;
         position++;
         direction--;
         break;
      case ':':
         cout << *dp;
         position++;
         break;
      case '.':
         cout << ((char) *dp);
         position++;
         break;
      case '{':
         if (*dp == 0){
            count++;
            temp = position;
            while (count != 0){
               if (symbol.at(position) == '{' && temp != position){
                  count++;
               }else if (symbol.at(position) == '}'){
                  count--;
               }
               ++position;
            }
         }
         else{
            position++;
         }
         break;
      case '}':
         count++;
         temp = position;
         while (count != 0){
            if (symbol.at(position) == '}' && temp != position){
               count++;
            }else if (symbol.at(position) == '{'){
               count--;
            }
            position--;
         }
         position++;
         break;
      default:
         position++;
         break;
      }
   }
}

void interactionMode(int* tape, int*& dp, int& count){
   string command;	//for users to input symbols
   char character;


   cout << "\nPosition:   ";
   for (int i = 0; i < 10; i++){
      if (count < 5)
         cout << setw(5) << right << i;
      else
         cout << setw(5) << right << i + count - 4;
   }
   cout << "\n";

   cout << "Character:  ";
   for (int i = 0; i < 10; i++){
      if (count < 5){
         if (tape[i] >= 33)
            cout << setw(5) << right << static_cast<char>(tape[i]);
         else
            cout << setw(5) << right << " ";
      }
      else{
         if (tape[i + count - 4] >= 33)
            cout << setw(5) << right << static_cast<char>(tape[i + count -4]);
         else
            cout << setw(5) << right << " ";
      }
   }
   cout << "\n";

   cout << "Integer:    ";
   for (int i = 0; i < 10; i++){
      if (count < 5)
         cout << setw(5) << right << tape[i];
      else
         cout << setw(5) << right << tape[i + count - 4];
   }
   cout << "\n";

   cout << "Pointer:    ";
   for (int i = 0; i < 10; i++){
      if (count >= 5){
         if (count >= 994)
            dp = &tape[999];
         if (&tape[i + count - 4] == dp)
            cout << setw(5) << right << '^';
         else
            cout << setw(5) << right << " ";
      }else{
         if (count <= 0){
            dp = tape;
            count = 0;
         }
         if (&tape[i] == dp)
            cout << setw(5) << right << '^';
         else
            cout << setw(5) << right << " ";
      }
   }
   cout << "\n";
   cout << ": ";
   cin >> command;

   vector<char> symbol;

   for (int i = 0; i < command.length(); i++){
      symbol.push_back(command[i]);
   }
   executeOutPut(symbol, dp, 0, 0, count);

   if (count > 994){
      count = 994;
   }
   interactionMode(tape, dp, count);
}

