import Lancer_simulations_sur_smt3

smt3_port=8080
numero_premiere_mission_elementaire_a_traiter = 0000
numero_derniere_mission_elementaire_a_traiter = 100000

def main():
    Lancer_simulations_sur_smt3.launch(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)

if __name__ == '__main__':
    main()
