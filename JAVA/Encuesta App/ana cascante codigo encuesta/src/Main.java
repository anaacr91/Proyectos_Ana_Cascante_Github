import java.util.Scanner;


public class Main {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		MuySatisfecho muySatisfecho = new MuySatisfecho();
		Satisfecho satisfecho =new Satisfecho();
		Insatisfecho insatisfecho = new Insatisfecho();
		MuyInsatisfecho muyInsatisfecho= new MuyInsatisfecho();
		Mensaje welcome = new Mensaje();

		boolean exit = false;
		boolean out = false;

		do {
			System.out.println(welcome.getMensaje());
			mostrarOpciones();
			timer();
		if(input.hasNextInt()) {
			switch (input.nextInt()) {

				case 1:
					System.out.println(muySatisfecho.getMensaje());
					exit = true;
					break;

				case 2:
					System.out.println(satisfecho.getMensaje());
					do {
						input.nextLine();
						improve(satisfecho);
						timer();
					if (input.hasNextInt()) {
						int note = input.nextInt();
						switch (note) {
							case 1, 2, 3:
								System.out.println("Thank you for your time. See you soon");
								exit=true;
								break;
							default:
								System.out.println("select a valid number");
								exit=false;}

						} else {
							System.out.println("don't enter a letter");
							exit=false;}

					} while (!exit);
						if (exit==true){
						break;}

				case 3:
					System.out.println(insatisfecho.getMensaje());
					exit = true;
					break;

				case 4:
					System.out.println(muyInsatisfecho.getMensaje());
					exit = true;
					break;

				case 5:
					System.out.println("Exit program");
					exit = true;
					break;

				case 999:
						System.out.println("Welcome to mantainance option");
					do {
						manteinance();
						timer();
						if (input.hasNextInt()) {
							int number = input.nextInt();
							input.nextLine();

							switch (number) {
								case 1:
									System.out.println("Insert new welcome message");
									welcome.setMensaje(input.nextLine());
									exit = true;
									out = false;
									break;

								case 2:
									System.out.println("Insert new satisfaction option 1 ");
									satisfecho.setOption1(input.nextLine());
									exit = true;
									out = false;
									break;

								case 3:
									System.out.println("Insert new satisfaction option 2 ");
									satisfecho.setOption2(input.nextLine());
									exit = true;
									out = false;
									break;

								case 4:
									System.out.println("Insert new satisfaction option 3 ");
									satisfecho.setOption3(input.nextLine());
									exit = true;
									out = false;
									break;

								case 5:
									System.out.println("Insert new Insatisfecho URL ");
									insatisfecho.setMensaje(input.nextLine());
									exit = true;
									out = false;
									break;

								case 6:
									System.out.println("Insert new Muy Insatisfecho URL ");
									muyInsatisfecho.setMensaje(input.nextLine());
									exit = true;
									out = false;
									break;

								case 7:
									System.out.println("Exit");
									exit = true;
									out = true;
									break;

								default:
									System.out.println("select a valid number");
									exit = false;
									out = false;
									break;}

						}else{
							System.out.println("don't enter a letter");
							exit = false;
							out = false;
							input.next();}

					} while (!out);
					exit = false;
					break;

				default:
					System.out.println("select a valid number");
					input.nextLine();
					exit = false;
					break;}


			}else{
				System.out.println("don't enter a letter");
				input.nextLine();}

		} while (!exit);
		input.nextLine();

	}

	public static void mostrarOpciones() {
		System.out.println("Please rate our service today. Select an option");
		System.out.println(
				"1. Muy Satisfecho\n" +
						"2. Satisfecho\n" +
						"3. Insatisfecho\n" +
						"4. Muy insatisfecho\n" +
						"5. Salir");
	}
	public static void improve(Satisfecho satisfecho){
		System.out.println("What can be improveed?");
		System.out.println("1. "+ satisfecho.getOption1());
		System.out.println("2. "+satisfecho.getOption2());
		System.out.println("3. "+satisfecho.getOption3());

	}
	public static void manteinance(){
		System.out.println("What do you want to change");
		System.out.println(
				"1. Welcome message\n" +
						"2. Satisfecho Option 1\n" +
						"3 Satisfecho Option 2\n" +
						"4 Satisfecho Option 3\n"+
						"5. Insatisfecho URL\n" +
						"6. Muy insatisfecho URL\n" +
						"7. Salir");
	}
	public static void timer(){
		System.out.println("\u001B[34m"+"Start Timeout 5 seconds"+"\u001B[30m");
		//este método simula los 5 segundos de espera del timeout, para que el usuario introduzca un dato y sino se volvería a la pantalla inicial
		//no tengo conocimientos necesarios para abrir hilos, que es la unica forma que he encontrado de hacer el timeout


	}
}










