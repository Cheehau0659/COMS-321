/**
 * @author cheehau@iastate.edu
 * @author jkeld@iastate.edu
 * @author ktran@iastate.edu
 * @author evelyn@iastate.edu
 */

package disassembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;

public class main {
	
	public static String findReg(String regNum) {
		int num = Integer.parseInt(regNum, 2);
		String reg = "X"+ num;
		if(num == 31)
			reg = "XZR";

		return reg;
	}

	public static String translateAddress(String addr){
		int num = Integer.parseInt(addr, 2);
		String result = "#"+num;
		return result;
	}
	
	public static String findImmediate(String regNum) {
		int num = Integer.parseInt(regNum, 2);	
		String reg = "#"+ num;
		return reg;
	}

	public static void main(String[] args) throws IOException {
		
		// take in file
		// code each opcode for each function
			// for register file 
			// shift amount
			// address 
		// save all info in file
		// display the file
		
		
		String fileName = args[0];
		File codeFile = new File(fileName);

		
		FileWriter writer =  new FileWriter("NewFile.txt");
		FileWriter writerBinary =  new FileWriter("binary.txt");
		
		
		FileInputStream stream = new FileInputStream(codeFile);
		String temp;
		String funcName;
		String rm;
		String shamt;
		String rn;
		String rd;
		String rt;
		String address;
		String alu;
		String immediate;
		String op;
		
		
		while(stream.available() >= 4) {
			for(int i = 0; i < 4; i++) {
				writerBinary.write(String.format("%8s", Integer.toBinaryString(stream.read() & 0xFF)).replace(' ', '0'));
				
			}
			if(stream.available() > 0)
				writerBinary.write("\n");
		}
		while(stream.available() > 0) {
			
			writerBinary.write(String.format("%8s", Integer.toBinaryString(stream.read() & 0xFF)).replace(' ', '0'));
				
			
		}
		writerBinary.close();
		File binaryFile = new File("binary.txt");
		Scanner fileReader = new Scanner(binaryFile);
		while(fileReader.hasNextLine()) {
			
			String data = fileReader.nextLine();
			String opcode = "";
			temp = data.substring(0,11);
			
			switch(temp) {

				case "10001011000": //ADD
					opcode = "10001011000"; // int out of range
					funcName = "ADD ";
					rm =  data.substring(11, 16);
					shamt = data.substring(16, 22);
					rn = data.substring(22, 27);
					rd = data.substring(27, 32);

					writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
					break;

				case "10001010000": //AND
					opcode = "10001010000";
					funcName = "AND ";
					rm =  data.substring(11, 16);
					shamt = data.substring(16, 22);
					rn = data.substring(22, 27);
					rd = data.substring(27, 32);

					writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
					break;

				case "11001010000": //EOR
					opcode = "11001010000";
					funcName = "EOR ";
					rm =  data.substring(11, 16);
					shamt = data.substring(16, 22);
					rn = data.substring(22, 27);
					rd = data.substring(27, 32);

					writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
					break;

				case "11010011011": //LSL
					opcode = "11010011011";
					funcName = "LSL ";
					rm =  data.substring(11, 16);
					shamt = data.substring(16, 22);
					rn = data.substring(22, 27);
					rd = data.substring(27, 32);

					writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+"," + "\n" );
					break;

				case "11010011010": //LSR
					opcode = "11010011010";
					funcName = "LSR ";
					rm =  data.substring(11, 16);
					shamt = data.substring(16, 22);
					rn = data.substring(22, 27);
					rd = data.substring(27, 32);

					writer.write(opcode + rm +shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+"," + "\n" );
					break;

				case "10101010000": //ORR
					opcode = "10101010000";
					funcName = "ORR ";
					rm =  data.substring(11, 16);
					shamt = data.substring(16, 22);
					rn = data.substring(22, 27);
					rd = data.substring(27, 32);

					writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
					break;

				case "11111000000": //STUR
					opcode = "11111000000";
					funcName = "STUR ";
					address = data.substring(11, 20);
					op =  data.substring(20, 22);
					rn = data.substring(22, 27);
					rt = data.substring(27, 32);
					writer.write(opcode + address + op + rn + rt +"\t" + funcName + findReg(rn)+","+ "[" + findReg(rt) + translateAddress(address) + "]" + "\n" );
					break;

				case "11111000010": //LDUR
					opcode = "11111000010";
					funcName = "LDUR ";
					address = data.substring(11, 20);
					op =  data.substring(20, 22);
					rn = data.substring(22, 27);
					rt = data.substring(27, 32);
					writer.write(opcode + address + op + rn + rt +"\t" + funcName + findReg(rt)+","+ "[" + findReg(rn) + translateAddress(address) + "]" + "\n" );
					break;



				case "11010110000": //BR
					opcode = "11010110000";
					funcName = "BR ";
					rm =  data.substring(11, 16);
					shamt = data.substring(16, 22);
					rn = data.substring(22, 27);
					rd = data.substring(27, 32);

					writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
					break;
					
				case "11001011000":
				opcode = "10001011000"; // int out of range
				funcName = "SUB ";
				rm =  data.substring(11, 16);
				shamt = data.substring(16, 22);
				rn = data.substring(22, 27);
				rd = data.substring(27, 32);
				
				
				writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
				break;
				
			case "11101011000":
				opcode = "11101011000"; // int out of range
				funcName = "SUBS ";
				rm =  data.substring(11, 16);
				shamt = data.substring(16, 22);
				rn = data.substring(22, 27);
				rd = data.substring(27, 32);
				
				
				writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
				break;
			case "10011011000":
				opcode = "10011011000"; // int out of range
				funcName = "MUL ";
				rm =  data.substring(11, 16);
				shamt = data.substring(16, 22);
				rn = data.substring(22, 27);
				rd = data.substring(27, 32);
				
				
				writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findReg(rm)+"\n" );
				break;
			case "11111111101":
				opcode = "11111111101"; // int out of range
				funcName = "PRNT ";
				rm =  data.substring(11, 16);
				shamt = data.substring(16, 22);
				rn = data.substring(22, 27);
				rd = data.substring(27, 32);
				
				
				writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName + findReg(rd)+"\n" );
				break;
			case "11111111100":
				opcode = "11111111100"; // int out of range
				funcName = "PRNL ";
				rm =  data.substring(11, 16);
				shamt = data.substring(16, 22);
				rn = data.substring(22, 27);
				rd = data.substring(27, 32);
				
				
				writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName +"\n" );
				break;
				
			case "11111111110":
				opcode = "11111111110"; // int out of range
				funcName = "DUMP ";
				rm =  data.substring(11, 16);
				shamt = data.substring(16, 22);
				rn = data.substring(22, 27);
				rd = data.substring(27, 32);
				
				
				writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName +"\n" );
				break;
			case "11111111111":
				opcode = "11111111111"; // int out of range
				funcName = "HALT ";
				rm =  data.substring(11, 16);
				shamt = data.substring(16, 22);
				rn = data.substring(22, 27);
				rd = data.substring(27, 32);
				
				
				writer.write(opcode + rm + shamt + rn + rd +"\t" + funcName +"\n" );
				break;

				//opcode didn't match must be smaller
				default:
					temp = data.substring(0,10);
					//for opcodes with length 10 ADDI/SUBI etc.
					switch(temp) {
						case "1001000100": //ADDI
							opcode = "1001000100";
							funcName = "ADDI ";
							alu = data.substring(10, 22);
							rn = data.substring(22, 27);
							rd = data.substring(27, 32);

							writer.write(opcode + alu + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ translateAddress(alu)+"\n" );
							break;


						case "1001001000": //ANDI
							opcode = "1001001000";
							funcName = "ANDI ";
							alu = data.substring(10, 22);
							rn = data.substring(22, 27);
							rd = data.substring(27, 32);

							writer.write(opcode + alu + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ translateAddress(alu)+"\n" );
							break;

						case "1101001000": //EORI
							opcode = "1101001000";
							funcName = "EORI ";
							alu = data.substring(10, 22);
							rn = data.substring(22, 27);
							rd = data.substring(27, 32);

							writer.write(opcode + alu + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ translateAddress(alu)+"\n" );
							break;

						case "1011001000": //ORRI
							opcode = "1011001000";
							funcName = "ORRI ";
							alu = data.substring(10, 22);
							rn = data.substring(22, 27);
							rd = data.substring(27, 32);

							writer.write(opcode + alu + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ translateAddress(alu)+"\n" );
							break;

                        case "1101000100":
						opcode = "1101000100";
						funcName = "SUBI ";
						immediate =  data.substring(10, 22);
						rn = data.substring(22, 27);
						rd = data.substring(27, 32);
						writer.write(opcode + immediate + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findImmediate(immediate) +"\n" );						
						break;
						
					case "1111000100":
						opcode = "1111000100";
						funcName = "SUBIS";
						immediate =  data.substring(10, 22);
						rn = data.substring(22, 27);
						rd = data.substring(27, 32);
						writer.write(opcode + immediate + rn + rd +"\t" + funcName + findReg(rd) +"," + findReg(rn)+","+ findImmediate(immediate) +"\n" );
						break;

						default:
						//for opcodes with length 8 CNBZ / CBZ
						 temp = data.substring(0,8);
						 switch(temp) {
							 case "01010100": //b.cond
								 opcode = "01010100";
								 funcName = "B. ";
								 address = data.substring(8, 27);
								 rt = data.substring(27, 32);
								 if(rt.equals("00000")){funcName = "B.EQ "; }
								 else if(rt.equals("00001")) {funcName = "B.NE "; }
								 else if(rt.equals("00010")) {funcName = "B.HS "; }
								 else if(rt.equals("00011")) {funcName = "B.LO "; }
								 else if(rt.equals("00100")) {funcName = "B.MI "; }
								 else if(rt.equals("00101")) {funcName = "B.PL "; }
								 else if(rt.equals("00110")) {funcName = "B.VS "; }
								 else if(rt.equals("00111")) {funcName = "B.VC "; }
								 else if(rt.equals("01000")) {funcName = "B.HI "; }
								 else if(rt.equals("01001")) {funcName = "B.LS "; }
								 else if(rt.equals("01010")) {funcName = "B.GE "; }
								 else if(rt.equals("01011")) {funcName = "B.LT "; }
								 else if(rt.equals("01100")) {funcName = "B.GT "; }
								 else if(rt.equals("01101")) {funcName = "B.LE "; }

//								 String convertedAdd = translateAddress(address);
								 writer.write(opcode + address + rt + "\t" + funcName + translateAddress(address) + "\n");
								 break;
							 
							 case "10110100": //CBZ
								 opcode =  "10110100";
								 funcName = "CBZ ";
								 address = data.substring(8, 27);
								 rt = data.substring(27, 32);
								 
								 writer.write(opcode + address + rt + "\t" + funcName + findReg(rt)+"," +translateAddress(address) + "\n");								
								 break;
							 
							 case "10110101": //CBNZ
								 opcode =  "10110101";
								 funcName = "CBNZ ";
								 address = data.substring(8, 27);
								 rt = data.substring(27, 32);
								 
								 writer.write(opcode + address + rt + "\t" + funcName + findReg(rt)+"," +translateAddress(address) + "\n");
								
								 break;

						 	default:
						 		//for opcodes with length 6 B / BL
						 		temp = data.substring(0,6);
								 switch(temp) {
									 case "000101": //B
									 opcode = "000101";
									 funcName = "B ";
									 address = data.substring(6, 32);
//									 String convertedAdd = translateAddress(address);

									 writer.write(opcode + address +"\t" + funcName + translateAddress(address) + "\n" );
									 break;

									 case "100101": //BL
										 opcode = "100101";
										 funcName = "BL ";
										 address = data.substring(6, 32);
//										 String convertedAdd = translateAddress(address);

										 writer.write(opcode + address +"\t" + funcName + translateAddress(address) + "\n" );
										 break;
										 
										 default:
											 writer.write(data +"\t" + "Code doesnt exist\n" );
											 break;
								 		
								 }
						 }
				
					}
			}
		}

		fileReader.close();
		writer.close();
		
		  File savedFile = new File("NewFile.txt");
		  Scanner sc = new Scanner(savedFile);
		  System.out.println();
		  while(sc.hasNextLine())
		  System.out.println(sc.nextLine());
		  sc.close();
		
	}
	
}
