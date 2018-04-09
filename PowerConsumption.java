/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerconsumption;

/**
 *
 * @author Patrícia
 */
import java.util.Scanner;
public class PowerConsumption {

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
        int numeroPessoas, numeroComodos, erro; //Declaração das variáveis inteiras, sendo o erro uma variável que utilizaremos para o caso em que o usuário digitar uma entrada inválida.
        String nomeComodo; //Declaração da variável que armazenará o nome dos cômodos da casa 
        Scanner buffer = new Scanner(System.in);
        Scanner buffer2 = new Scanner(System.in);
        
        System.out.println("CONSUMO DE ENERGIA ELÉTRICA EM UMA RESIDÊNCIA\n\n");

        System.out.println("-> Digite o número de pessoas no seu núcleo familiar, ou o grupo de pessoas com quem mora: ");
        numeroPessoas = buffer.nextInt();
        
        System.out.println("\n\n");
        
        System.out.println("Lista de cômodos disponíveis para consulta: quarto, banheiro, sala de estar, cozinha (sala de jantar inclusa), lavanderia, escritorio, area externa.\n");
        
        System.out.println("-> Digite a quantidade de cômodos presentes em sua casa: ");
        numeroComodos = buffer.nextInt();
        
        System.out.println("\n\n\n");
        
        //Caso o usuário digite "0" como resposta para a perguntar anterior, o programa termina aqui
        if(numeroComodos == 0) {
            System.out.println("FIM");
            System.exit(0);
        }
        
        //Inicialização das variáveis de consumo de cada cômodo da casa
        double consumoq = 0, consumob = 0, consumoa = 0, consumos = 0, consumoe = 0, consumoc = 0, consumol = 0, consumototal, consumotarifado = 0, bandeira0;
        
        while (numeroComodos > 0) {
  
            //Entrada pelo usuário do nome de cada cômodo por vez
            System.out.println("-> Digite o nome do cômodo: ");
            nomeComodo = buffer2.nextLine();
            
            System.out.println("\n\n");
            
            if(nomeComodo.equals("quarto")) {
                consumoq = quarto();
            } else if (nomeComodo.equals("banheiro")) {
                consumob = banheiro();
            } else if (nomeComodo.equals("sala de estar")) {
                consumos = sala();
            } else if (nomeComodo.equals("lavanderia")) {
                consumol = lavanderia();
            } else if (nomeComodo.equals("escritorio")) {
                consumoe = escritorio();
            } else if (nomeComodo.equals("area externa")) {
                consumoa = area_externa();
            } else if (nomeComodo.equals("cozinha")) {
                consumoc = cozinha();
            } else {
                System.out.println("Entrada inválida, tente outra vez!!");
                //Adicionamos em 1 a variável numeroComodos para que a mesma fique com o valor intacto ao ser decrementada em 1 unidade a seguir
                numeroComodos++; 
            }
            //numeroComodos serve como contador que será decrementado em 1 unidade
            numeroComodos--;    
        }
        System.out.println("\n\n");
        
        
        //Cálculo do consumo total 
        consumototal = consumoq + consumob + consumos + consumol + consumoe + consumoa + consumoc;
        
        String bandeira, classe;
        erro = 1;
        
        while(erro == 1) {
            erro = 0;
            System.out.println("Agora vamos calcular a previsão da sua conta: ");
            System.out.println();
            System.out.println("Sua residência pertence à classe:\nresidencial normal? \nresidencial de baixa renda? \nrural?");
            System.out.println();
            System.out.println("Resposta: ");
            classe = buffer2.nextLine();
            
            //Cálculos do valor final tarifado para cada classe residencial, considerando a Tarifa do Uso do Sistema de Distribuição (TUSD) e Tarifa de Energia (TE), com os seus valores respectivos em R$/kWh:
           
            if(classe.equals("residencial de baixa renda")) {
                if(consumototal <= 30) {
                    consumotarifado = consumototal*(0.06747+0.08300);
                } else if(consumototal > 30 && consumototal <= 100) {
                    consumotarifado = consumototal*(0.11566+0.14229);
                } else if(consumototal > 100 && consumototal <= 220) {
                    consumotarifado = consumototal*(0.17348+0.21343);
                } else {
                    consumotarifado = consumototal*(0.19276+0.23715);
                }
            }
            
            else if(classe.equals("residencial normal")) {
                consumotarifado = consumototal*(0.19896+0.23715);
            }
            else if(classe.equals("rural")) {
                consumotarifado = consumototal*(0.13927+0.16600);
            }
            else {
                System.out.println("Erro!! Digite corretamente a sua classe! ");
                erro = 1;
            }
        }
        
        System.out.println("\n\n");
        
        //Acréscimo no valor do consumo total de acordo com a bandeira em que a conta da residência se encontra naquele mês
        bandeira0 = bandeiratarifaria(consumototal);
        
        consumotarifado = consumotarifado + bandeira0;
        
        
        System.out.println("O valor final já tarifado da conta será R$ " + String.format("%.2f", consumotarifado)+ "\n\n"); 

        
        double porcentagemq, porcentagemb, porcentagems, porcentageml, porcentageme, porcentagema, porcentagemc;
        
        //Estruturas condicionais para calcularmos a contribuição individual de casa cômodo no consumo total
        if(consumoq != 0) {
            porcentagemq = consumoparcial(consumototal, consumoq);
            System.out.println("Contribuição parcial do quarto: " + String.format("%.3f",porcentagemq*100) + " %");
        }
        
        if(consumob != 0) {
            porcentagemb = consumoparcial(consumototal, consumob);
            System.out.println("Contribuição parcial do banheiro: " + String.format("%.3f", porcentagemb*100) + " %");
        }
        
        if (consumos != 0) {
            porcentagems = consumoparcial(consumototal, consumos);
            System.out.println("Contribuição parcial da sala de estar: " + String.format("%.3f", porcentagems*100) + " %");
        }
        
        if (consumol != 0) {
            porcentageml = consumoparcial(consumototal, consumol);
            System.out.println("Contribuição parcial da lavanderia: " + String.format("%.3f", porcentageml*100) + " %");
        }
        
        if (consumoe != 0) {
            porcentageme = consumoparcial(consumototal, consumoe);
            System.out.println("Contribuição parcial do escritório: " + String.format("%.3f", porcentageme*100) + " %");
        }
        
        if (consumoa != 0) {
            porcentagema = consumoparcial(consumototal, consumoa);
            System.out.println("Contribuição parcial da área externa: " + String.format("%.3f", porcentagema*100) + " %");
        }
        
        if (consumoc != 0) {
            porcentagemc = consumoparcial(consumototal, consumoc);
            System.out.println("Contribuição parcial da cozinha: " + String.format("%.3f", porcentagemc*100) + " %");
        }
        
        System.out.println("\n\n");
        
        double mediaPorPessoa = consumototal/numeroPessoas;
        
        System.out.println("Média de consumo de cada integrante na residência: " + mediaPorPessoa + " kWh/mês por pessoa.\n");
    }
    
    static double bandeiratarifaria(double consumototal){
        int erro = 1;
        double consumotarifado_bandeira = 0;
        String bandeira;
        Scanner buffer = new Scanner(System.in);
        
        while(erro == 1) {
            erro = 0;
            System.out.println("Que bandeira será tarifada na conta para este mês segundo a prestadora de serviços para consumo elétrico da sua região?\nvermelha\namarela\nverde");
            System.out.println();
            System.out.println("Opção: ");
            bandeira = buffer.nextLine();
        
            if(bandeira.equals("vermelha")) {
                consumotarifado_bandeira = consumototal*0.045;
            } else if(bandeira.equals("amarela")) {
                consumotarifado_bandeira = consumototal*0.015;
            } else if(bandeira.equals("verde")) {
                consumotarifado_bandeira = consumototal;
            } else {
                System.out.println("Erro!! Digite novamente!");
                erro = 1;
            }
        }
        
        return consumotarifado_bandeira;
    }
    
    
    public static double consumoparcial (double consumototal, double consumoDoComodo) {
        
        return consumoDoComodo/consumototal;
    }
    
    public static double ListagemObjetos(String nomeComodo[], int pot[]) {
        int erro = 1;
        double horas[] = new double[nomeComodo.length], consumototal = 0, consumoComodo[] = new double [nomeComodo.length];
        Scanner buffer = new Scanner (System.in);
        
        while(erro == 1) {
            System.out.println("Listagem de objetos que consomem energia elétrica: ");
            System.out.println();

            for(int ind = 0; ind < nomeComodo.length; ind++) {
                
                System.out.println("Nome: " + nomeComodo[ind]);
                
                System.out.println("Quantidade de horas (por semana) em que o objeto é utilizado: ");
                horas[ind] = buffer.nextDouble();
                
                System.out.println();
                
                consumoComodo[ind] = (horas[ind]*4*pot[ind])/1000;
                
                consumototal = consumototal + consumoComodo[ind];
            } 
            
            System.out.println("Digitou algum valor errado e gostaria de refazer esta seção?\nDigite 1 para sim e 0 para não");
            erro = buffer.nextInt();
            
            if (erro == 1 ) {
                consumototal = 0;
            } else if (erro != 1 && erro != 0) {
                erro = 1;
                System.out.println("\nErro! Tente outra vez!! \n");
                System.out.println("Digitou algum valor errado e gostaria de refazer esta seção?\nDigite 1 para sim e 0 para não");
                erro = buffer.nextInt();
            }
            
            System.out.println("\n\n");
        }
        
        return consumototal;
    }
    
    
    public static double quarto() {
        double consumoq;
        String q[] = new String [23];
        int pot[] = new int [23];
        
        //Descrição dos objetos que consomem energia elétrica
        q[0] = "Lâmpada incandescente 40W";
        q[1] = "Lâmpada incandescente 60W";
        q[2] = "Lâmpada incandescente 100W";
        q[3] = "Lâmpada fluorescente 11W";
        q[4] = "Lâmpada fluorescente 15W";
        q[5] = "Lâmpada fluorescente 23W";
        q[6] = "Ar condicionado 7.500BTU";
        q[7] = "Ar condicionado 10.000BTU";
        q[8] = "Ar condicionado 12.000BTU";
        q[9] = "Ar condicionado 15.00BTU";
        q[10] = "Ar condicionado 18.000BTU";
        q[11] = "Computador";
        q[12] = "Impressora";
        q[13] = "Estabilizador";
        q[14] = "Frigobar";
        q[15] = "Microcomputador";
        q[16] = "Tv 14’’";
        q[17] = "Tv 18’’";
        q[18] = "Tv 20’’";
        q[19] = "Tv 29’’";
        q[20] = "Ventilador de teto";
        q[21] = "Ventilador pequeno";
        q[22] = "Videogame";
        
        //Quantidade da potência correspondente (em watts) de cada índice no vetor q
        pot[0] = 40;
        pot[1] = 60;
        pot[2] = 100;
        pot[3] = 11;
        pot[4] = 15;
        pot[5] = 23;
        pot[6] = 1000;
        pot[7] = 1350;
        pot[8] = 1450;
        pot[9] = 2000;
        pot[10] = 2100;
        pot[11] = 180;
        pot[12] = 180;
        pot[13] = 180;
        pot[14] = 70;
        pot[15] = 120;
        pot[16] = 60;
        pot[17] = 70;
        pot[18] = 90;
        pot[19] = 110;
        pot[20] = 120;
        pot[21] = 65;
        pot[22] = 15;
        
        consumoq = ListagemObjetos(q, pot);
        
        return consumoq;
    }
    
    static double banheiro() {
        double consumob;
        String b[] = new String [13]; 
        int pot[] = new int [13];
        
        //Descrição dos objetos que consomem energia elétrica
        b[0] = "Lâmpada incandescente 40W";
        b[1] = "Lâmpada incandescente 60W";
        b[2] = "Lâmpada incandescente 100W";
        b[3] = "Lâmpada fluorescente 11W";
        b[4] = "Lâmpada fluorescente 15W";
        b[5] = "Lâmpada fluorescente 23W";
        b[6] = "Barbeador";
        b[7] = "Depilador";
        b[8] = "Massageador";
        b[9] = "Chuveiro";
        b[10] = "Escova de dentes elétrica";
        b[11] = "Secador de cabelo";
        b[12] = "Torneira eletrica";
        
        //Quantidade da potência correspondente (em watts) de cada índice no vetor b
        pot[0] = 40;
        pot[1] = 60;
        pot[2] = 100;
        pot[3] = 11;
        pot[4] = 15;
        pot[5] = 23;
        pot[6] = 10;
        pot[7] = 10;
        pot[8] = 10;
        pot[9] = 3500;
        pot[10] = 50;
        pot[11] = 600;
        pot[12] = 3500;
        
        consumob = ListagemObjetos(b, pot);
        
        return consumob;   
    }
    
    static double cozinha() {
        double consumoc;
        String c[] = new String [34];
        int pot[]=new int [34];
        
        //Descrição dos objetos que consomem energia elétrica
        c[0] = "Lâmpada incandescente 40W";
        c[1] = "Lâmpada incandescente 60W";
        c[2] = "Lâmpada incandescente 100W";
        c[3] = "Lâmpada fluorescente 11W";
        c[4] = "Lâmpada fluorescente 15W";
        c[5] = "Lâmpada fluorescente 23W";
        c[6] = "Abridor/afiador";
        c[7] = "Afiador de facas";
        c[8] = "Aquecedor de mamadeira";
        c[9] = "Batedeira";
        c[10] = "Boiler(50 e 60)";
        c[11] = "Boiler (100)";
        c[12] = "Exaustor fogão";
        c[13] = "Exaustor parede";
        c[14] = "Faca elétrica";
        c[15] = "Fogão comum";
        c[16] = "Fogão elétrico";
        c[17] = "Forno a resistência grande";
        c[18] = "Forno a resistência pequeno";
        c[19] = "Freezer";
        c[20] = "Fritadeira elétrica";
        c[21] = "Geladeira 1 porta";
        c[22] = "Geladeira 2 portas";
        c[23] = "Grill";
        c[24] = "Yogurteira";
        c[25] = "Lavadeira de louca";
        c[26] = "Liquidificador";
        c[27] = "Moedor de carne";
        c[28] = "Multiprocessador";
        c[29] = "Panela elétrica";
        c[30] = "Pipoqueira";
        c[31] = "Sorveteira";
        c[32] = "Torneira elétrica";
        c[33] = "Torradeira";
        
        //Quantidade da potência correspondente (em watts) de cada índice no vetor c
        pot[0] = 40;
        pot[1] = 60;
        pot[2] = 100;
        pot[3] = 11;
        pot[4] = 15;
        pot[5] = 23;
        pot[6] = 135;
        pot[7] = 20;
        pot[8] = 100;
        pot[9] = 120;
        pot[10] = 1500;
        pot[11] = 2030;
        pot[12] = 170;
        pot[13] = 110;
        pot[14] = 220;
        pot[15] = 60;
        pot[16] = 9120;
        pot[17] = 1500;
        pot[18] = 800;
        pot[19] = 130;
        pot[20] = 1000;
        pot[21] = 15;
        pot[22] = 90;
        pot[23] = 120;
        pot[24] = 26;
        pot[25] = 1500;
        pot[26] = 300;
        pot[27] = 320;
        pot[28] = 420;
        pot[29] = 1100;
        pot[30] = 1100;
        pot[31] = 15;
        pot[32] = 3500;
        pot[33] = 800;
       
        consumoc = ListagemObjetos(c, pot);
        
        return consumoc;
    }
    
    static double sala() {
        double consumos;
        String s[] = new String [33];
        int pot[]=new int [33];  
        
        //Descrição dos objetos que consomem energia elétrica
        s[0] = "Lâmpada incandescente 40W";
        s[1] = "Lâmpada incandescente 60W";
        s[2] = "Lâmpada incandescente 100W";
        s[3] = "Lâmpada fluorescente 11W";
        s[4] = "Lâmpada fluorescente 15W";
        s[5] = "Lâmpada fluorescente 23W";
        s[6] = "Aparelho de som(3em1)";
        s[7] = "Aparelho de som pequeno";
        s[8] = "Aquecedor de ambiente";
        s[9] = "Ar condicionado 7.500BTU";
        s[10] = "Ar condicionado 10.000BTU";
        s[11] = "Ar condicionado 12.000BTU";
        s[12] = "Ar condicionado 15.00BTU";
        s[13] = "Ar condicionado 18.000BTU";
        s[14] = "Bomba de aquário pequena";
        s[15] = "Bomba de aquário grande";
        s[16] = "Circulador de ar";
        s[17] = "Enceradeira";
        s[18] = "Maquina de costura";
        s[19] = "Maquina de furar";
        s[20] = "Nebulizador";
        s[21] = "Ozonizador";
        s[22] = "Radio elétrico pequeno";
        s[23] = "Radio elétrico grande";
        s[24] = "Secretaria eletrônica";
        s[25] = "Tv 14’’";
        s[26] = "Tv 18’’";
        s[27] = "Tv 20’’";
        s[28] = "Tv 29’’";
        s[29] = "Ventilador de teto";
        s[30] = "Ventilador pequeno";
        s[31] = "Videocasset";
        s[32] = "Videogame";
        
        //Quantidade da potência correspondente (em watts) de cada índice no vetor s
        pot[0] = 40;
        pot[1] = 60;
        pot[2] = 100;
        pot[3] = 11;
        pot[4] = 15;
        pot[5] = 23;
        pot[6] = 80;
        pot[7] = 20;
        pot[8] = 1550;
        pot[9] = 1000;
        pot[10] = 1350;
        pot[11] = 1450;
        pot[12] = 2000;
        pot[13] = 2100;
        pot[14] = 10;
        pot[15] = 5;
        pot[16] = 200;
        pot[17] = 500;
        pot[18] = 100;
        pot[19] = 350;
        pot[20] = 40;
        pot[21] = 100;
        pot[22] = 45;
        pot[23] = 10;
        pot[24] = 20;
        pot[25] = 60;
        pot[26] = 70;
        pot[27] = 90;
        pot[28] = 110;
        pot[29] = 120;
        pot[30] = 65;
        pot[31] = 10;
        pot[32] = 15;
       
        consumos = ListagemObjetos(s, pot);
        
        return consumos;
    }
    
    static double lavanderia() {
        double consumol;
        String l[] = new String [11];
        int pot[]=new int [11];
        
        //Descrição dos objetos que consomem energia elétrica
        l[0] = "Lâmpada incandescente 40W";
        l[1] = "Lâmpada incandescente 60W";
        l[2] = "Lâmpada incandescente 100W";
        l[3] = "Lâmpada fluorescente 11W";
        l[4] = "Lâmpada fluorescente 15W";
        l[5] = "Lâmpada fluorescente 23W";
        l[6] = "Aspirador de pó";
        l[7] = "Ferro elétrico";
        l[8] = "Lavadora de roupas";
        l[9] = "Secador de roupa pequena";
        l[10] = "Secador de roupa grande";
        
        //Quantidade da potência correspondente (em watts) de cada índice no vetor l
        pot[0] = 40;
        pot[1] = 60;
        pot[2] = 100;
        pot[3] = 11;
        pot[4] = 15;
        pot[5] = 23;
        pot[6] = 100;
        pot[7] = 1000;
        pot[8] = 500;
        pot[9] = 1000;
        pot[10] = 3500;
       
        consumol = ListagemObjetos(l, pot);

        return consumol;
    }
    
    
    static double escritorio() {
        double consumoe;
        String e[] = new String [19];
        int pot[]=new int [19];
        
        //Descrição dos objetos que consomem energia elétrica
        e[0] = "Lâmpada incandescente 40W";
        e[1] = "Lâmpada incandescente 60W";
        e[2] = "Lâmpada incandescente 100W";
        e[3] = "Lâmpada fluorescente 11W";
        e[4] = "Lâmpada fluorescente 15W";
        e[5] = "Lâmpada fluorescente 23W";
        e[6] = "Ar condicionado 7.500BTU";
        e[7] = "Ar condicionado 10.000BTU";
        e[8] = "Ar condicionado 12.000BTU";
        e[9] = "Ar condicionado 15.00BTU";
        e[10] = "Ar condicionado 18.000BTU";
        e[11] = "Computador";
        e[12] = "Impressora";
        e[13] = "Estabilizador";
        e[14] = "Frigobar";
        e[15] = "Microcomputador";
        e[16] = "Secretaria eletrônica";
        e[17] = "Ventilador de teto";
        e[18] = "Ventilador pequeno";
        
        //Quantidade da potência correspondente (em watts) de cada índice no vetor e
        pot[0] = 40;
        pot[1] = 60;
        pot[2] = 100;
        pot[3] = 11;
        pot[4] = 15;
        pot[5] = 23;
        pot[6] = 1000;
        pot[7] = 1350;
        pot[8] = 1450;
        pot[9] = 2000;
        pot[10] = 2100;
        pot[11] = 180;
        pot[12] = 180;
        pot[13] = 180;
        pot[14] = 70;
        pot[15] = 120;
        pot[16] = 20;
        pot[17] = 120;
        pot[18] = 65;
       
        consumoe = ListagemObjetos(e, pot);
        
        return consumoe;
    }
    
    static double area_externa() {
        double consumoa;
        String a[] = new String [9];
        int pot[]=new int [9];
        
        //Descrição dos objetos que consomem energia elétrica
        a[0] = "Lâmpada incandescente 40W";
        a[1] = "Lâmpada incandescente 60W";
        a[2] = "Lâmpada incandescente 100W";
        a[3] = "Lâmpada fluorescente 11W";
        a[4] = "Lâmpada fluorescente 15W";
        a[5] = "Lâmpada fluorescente 23W";
        a[6] = "Churrasqueira";
        a[7] = "Cortador de grama pequeno";
        a[8] = "Cortador de grama grande";
        
        //Quantidade da potência correspondente (em watts) de cada índice no vetor a
        pot[0] = 40;
        pot[1] = 60;
        pot[2] = 100;
        pot[3] = 11;
        pot[4] = 15;
        pot[5] = 23;
        pot[6] = 3800;
        pot[7] = 500;
        pot[8] = 1140;
       
        consumoa = ListagemObjetos(a, pot);
        
        return consumoa;
    }
}  
