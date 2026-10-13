from logger import logger_config

from stsloganalyzis.unisig.s98 import decode_unisig_s98

OUTPUT_DIRECTORY = "output"


@logger_config.stopwatch_decorator(monitor_ram_usage=True)
def handle_directory(directory_full_path: str, filename_pattern: str = "*") -> decode_unisig_s98.UnisigS98Simulation:
    unisig_simulation = decode_unisig_s98.UnisigS98Simulation()
    unisig_simulation.build_unisig_s98_packets_from_load_pcap_files_in_directory(directory_full_path, filename_pattern)
    common_actions(unisig_simulation)
    logger_config.print_and_log_error_if(not unisig_simulation.unisig_s98_packets, f"No packet found in {directory_full_path}")
    return unisig_simulation


@logger_config.stopwatch_decorator(monitor_ram_usage=True)
def handle_file(file_full_path: str) -> decode_unisig_s98.UnisigS98Simulation:
    unisig_simulation = decode_unisig_s98.UnisigS98Simulation()
    unisig_simulation.build_unisig_s98_packets_from_load_pcap_file(file_full_path)
    common_actions(unisig_simulation)
    logger_config.print_and_log_error_if(not unisig_simulation.unisig_s98_packets, f"No packet found in {file_full_path}")
    return unisig_simulation


def common_actions(unisig_simulation: decode_unisig_s98.UnisigS98Simulation) -> None:
    unisig_simulation.recompute_all_mac()
    if len(unisig_simulation.unisig_s98_packets) < 10000:
        unisig_simulation.dump_packets_as_json(output_directory=OUTPUT_DIRECTORY)
    unisig_simulation.save_all_packets_as_reports()
    unisig_simulation.save_all_packets_with_bad_mac_with_history_as_reports()
    unisig_simulation.save_all_but_dt_packets()
    unisig_simulation.save_all_protocolar_or_invalid_mac_packets()
    unisig_simulation.save_all_disconnections_with_context()
    unisig_simulation.save_all_packets_with_invalid_mac_with_context()


def main() -> None:
    with logger_config.application_logger():
        simulations: list[decode_unisig_s98.UnisigS98Simulation] = []
        simulations.append(handle_directory(r"C:\Users\fr232487\Downloads\test_nuit_20261012\PAS_01", "*"))  # contains mac errors
        simulations.append(handle_directory(r"C:\Users\fr232487\Downloads\test_nuit_20261012\PAS_02", "*"))  # contains mac errors
        simulations.append(handle_directory(r"C:\Users\fr232487\Downloads\logs_wsk_PAI75_22.09.2026", "*.pcap"))  # contains mac errors
        simulations.append(handle_file(r"D:\Github\yanndanielou-programmation\Python\STSLogAnalyzis\test\resources\unisig_s98\small_capture_started_after_connexion.pcapng"))
        simulations.append(handle_file(r"C:\Users\fr232487\Downloads\tg13zc1-pas3-cucp-a-m_pai+deconnexion+21h34\tg13zc1-pas3-cucp-a-m_pai deconnexion 21h34.pcap"))
        simulations.append(handle_directory(r"C:\Users\fr232487\Downloads\tg13zc1-pas3-cucp-a-m_pai+deconnexion+21h34"))
        simulations.append(handle_directory(r"C:\Users\fr232487\Downloads\2026-09-14 wiresharks pas pai raoul OneDrive_1_14-09-2026"))
        # handle_directory(r"C:\Users\fr232487\Downloads\2026_10_02_PAS_PAI_2") disabled because no data
        # simulations.append(handle_file(r"C:\Users\fr232487\Downloads\logs_wsk_PAI75_22.09.2026\log_PAS_PAI_22.09_00002_20260922145856.2026.pcap")) redundant with folder
        # handle_directory(r"C:\Users\fr232487\Downloads\2026_10_02_PAS_PAI_2\wireshark")

        all_unisig_packets = [unisig_packet for simulation in simulations for unisig_packet in simulation.unisig_s98_packets]

        decode_unisig_s98.save_all_disconnections_with_context(
            all_unisig_packets=all_unisig_packets,
            library_label="all libraries",
        )
        decode_unisig_s98.save_all_packets_with_invalid_mac_with_context(
            all_unisig_packets=all_unisig_packets,
            library_label="all libraries",
        )

        decode_unisig_s98.save_all_packets_as_reports(
            unisig_s98_packets_to_save=all_unisig_packets,
            library_label="all libraries",
        )

        decode_unisig_s98.save_all_packets_with_bad_mac_with_history_as_reports(
            all_unisig_packets=all_unisig_packets,
            library_label="all libraries",
        )

        decode_unisig_s98.save_all_but_dt_packets(
            all_unisig_packets=all_unisig_packets,
            library_label="all libraries",
        )

        decode_unisig_s98.save_all_protocolar_or_invalid_mac_packets(
            all_unisig_packets=all_unisig_packets,
            library_label="all libraries",
        )


if __name__ == "__main__":
    main()
