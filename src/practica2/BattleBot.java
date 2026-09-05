package practica2;

public class BattleBot {

    public static void main(String[] args) {

        InfoPropietario propietario1 =
                new InfoPropietario("Ana", "AAAA000000HDFXXX01");

        InfoPropietario propietario2 =
                new InfoPropietario("Luis", "BBBB000000HDFXXX02");

        InfoPropietario propietario3 =
                new InfoPropietario("Sofia", "CCCC000000HDFXXX03");


        Bateria bateria1 =
                new Bateria("Li-ion", 2025, "PowerBot", 100);

        Bateria bateria2 =
                new Bateria("Li-ion", 2024, "EnergyTech", 100);

        Bateria bateria3 =
                new Bateria("Li-Po", 2025, "RobotPower", 100);


        Robot robot1 =
                new Robot("Titan", "Pesado", 8.5, 6.0,
                        propietario1, bateria1);

        Robot robot2 =
                new Robot("Rayo", "Ligero", 4.2, 9.0,
                        propietario2, bateria2);

        Robot robot3 =
                new Robot("Fenix", "Mediano", 6.0, 7.0,
                        propietario3, bateria3);


        System.out.println("===== BATTLEBOTS =====");

        System.out.println("Robots participantes: "
                + Robot.getTotalRobots());


        System.out.println("\n--- ANTES DE LA BATALLA ---");

        System.out.println(robot1);
        System.out.println(robot2);
        System.out.println(robot3);


        System.out.println("\n--- INICIA LA BATALLA ---");

        robot1.avanzar(5);
        robot2.avanzar(5);
        robot3.avanzar(5);

        robot1.cambiarVelocidad(4.5);
        robot2.cambiarVelocidad(7.5);
        robot3.cambiarVelocidad(5.0);

        robot1.detener();


        System.out.println("\n--- DESPUÉS DE LA BATALLA ---");

        System.out.println(robot1);
        System.out.println(robot2);
        System.out.println(robot3);


        System.out.println("\n--- RENDIMIENTO ---");

        System.out.println("Titan: "
                + robot1.calcularRendimiento());

        System.out.println("Rayo: "
                + robot2.calcularRendimiento());

        System.out.println("Fenix: "
                + robot3.calcularRendimiento());


        Robot robotFavorito = robot1;

        robot1.cambiarVelocidad(2.0);

        System.out.println("\n--- PRUEBA DE REFERENCIAS ---");

        System.out.println("¿Son el mismo objeto? "
                + (robot1 == robotFavorito));

        System.out.println(robotFavorito);


        Robot robot4 =
                new Robot("Titan", "Pesado", 8.5, 6.0,
                        new InfoPropietario(
                                "Ana",
                                "AAAA000000HDFXXX01"),
                        new Bateria(
                                "Li-ion",
                                2025,
                                "PowerBot",
                                100));


        System.out.println("\n--- PRUEBA DE EQUALS ---");

        System.out.println("robot1.equals(robot4): "
                + robot1.equals(robot4));

        System.out.println("robot1.equals(robotFavorito): "
                + robot1.equals(robotFavorito));


        System.out.println("\nTotal de robots creados: "
                + Robot.getTotalRobots());
    }
}