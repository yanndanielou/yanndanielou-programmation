import ProduireSimplesRunsAndExporterSimplesRunsSimulations

smt3_port=8080
numero_premiere_mission_elementaire_a_traiter = 1
numero_derniere_mission_elementaire_a_traiter = 1000

def main():
    ProduireSimplesRunsAndExporterSimplesRunsSimulations.launch(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)

if __name__ == '__main__':
    main()
