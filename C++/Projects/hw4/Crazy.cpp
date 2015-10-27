#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <iomanip>
using namespace std;

void compileOutPut(vector<char>);
void executeOutPut(vector<char> symbol, int*& dp, int position, int count);
void interactionMode(int* tape, int*& dp, int count);

int main(int argc, char *argv[]){
   const int SIZE = 1000;
   int tape[SIZE] = {0};
   int *dp = tape;

   if (argc <= 1){   // Check if Null Pointer
      //interactione mode
      interactionMode(tape, dp, 0);
   }
   else{
      string arg = argv[1];
      string stringFile = argv[2];
      vector<char> symbol;

      if (arg == "-c"){ 
         ifstream fin(stringFile.c_str());  // Can now compile on g++
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
         if(fin.fail()){
            //Fail to read file
            cout << "Fail" << endl;
         }else{
            //Success read file
            char c;
            while(fin >> c){
               symbol.push_back(c);
            }
            executeOutPut(symbol, dp, 0, 0);
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

   for(size_t i = 0; i < symbol.size(); i++)  // std::size_t fixed warning
   {
      switch(symbol[i])
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

void executeOutPut(vector<char> symbol, int*& dp, int position, int count){
   int temp = 0;

   while (position < symbol.size())
   {
      switch(symbol[position])
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
         break;
      case '<':
         --dp;
         position++;
         break;
      case ':':
         cout << *dp << endl;
         position++;
         break;
      case '.':
         cout << ((char) *dp) << endl;
         position++;
         break;
      case '{':
         if(*dp == 0){
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

void interactionMode(int* tape, int*& dp, int count){
   string command;	//for users to input symbols
   const int SIZE = 1000;
   int position[SIZE];
   char pointer[SIZE];

   cout << "\nPosition:   ";
   for(int i = 0; i < 10; i++){	//only showing 10 cells at a time
      if(count >= 10){
         position[i] = i + count;
         cout << setw(5) << right << position[i] - 4;
      }else{
         position[i] = i;
         cout << setw(5) << right << position[i];
      }	
   }
   cout << "\n";

   cout << "Integer:    ";
   for(int i = 0; i < 10; i++){	//only showing 10 cells at a time
      if(count >= 10){
         cout<< setw(5) << right << tape[i + count - 4];
      }else{
         cout<< setw(5) << right << tape[i];
      }
   }
   cout << "\n";

   cout << "Character:  ";
   for(int i = 0; i < 10; i++){
      if(count >= 10){
         cout<< setw(5) << right << (char)tape[i + count - 4];
      }else{
         cout<< setw(5) << right << (char)tape[i];
      }
   }
   cout << "\n";

   cout << "Pointer:    ";
   for(int i = 0; i < 10; i++){
      if(count >= 10){
         if(count > 991){
            count = 991;
            dp = (tape + SIZE - 1);
         }
         if(&tape[i + count - 4] == dp){
            cout << setw(5) << right << '^';
         }else{
            cout << setw(5) << right << "X";
         }
      }else{
         if(count <= 0){
            dp = tape;
            count = 0;
         }
         if(&tape[i] == dp){
            cout << setw(5) << right << '^';
         }else{
            cout << setw(5) << right << "X";
         }
      }
   }
   cout << "\n";
   cout << ": ";
   cin >> command;

   vector<char> symbol;

   for(int i = 0; i < command.length(); i++){
      symbol.push_back(command[i]);
      if(command[i] == '>'){
         count++;
      }else if(command[i] == '<'){
         count--;
      }
   }
   if(count > 991){
      count = 991;
      dp = (tape + SIZE - 1);
   }
   executeOutPut(symbol, dp, 0, 0);
   interactionMode(tape, dp, count);
}

