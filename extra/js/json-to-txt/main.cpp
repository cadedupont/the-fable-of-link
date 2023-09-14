// Author: Cade DuPont
// Date: 04.22.23
// Description: Parse JSON file, format into 2D array and output to txt file

#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ifstream din;
    din.open("../../../src/map.json");

    if (din.fail()) {
        cout << "Error: could not open file." << endl;
        exit(1);
    }

    ofstream dout;
    dout.open("output.txt");

    // While there are still characters in the file, read the character.
    // If the character is a digit, parse the following characters that are also digits, write to file beginning with [ and ending with a comma
    // On the same line in the output file, parse the next characters that are also digits into a number, write to file ending with a ]
    while (din.peek() != EOF) {
        if (isdigit(din.peek())) {
            dout << "[";
            int num = 0;
            while (isdigit(din.peek()))
                num = num * 10 + (din.get() - '0');
            dout << num << ", ";

            num = 0;
            while (!isdigit(din.peek()))
                din.get();
            while (isdigit(din.peek()))
                num = num * 10 + (din.get() - '0');
            dout << num << "], ";
        } else {
            din.get();
        }
    }

    return 0;
}