"""
This script reads execution metrics from the sequential and concurrent
versions of the Merge Sort application, calculates the speed-up achieved by the
concurrent version, and writes the results to an output file.

The script processes metrics for cases A through J, calculating the speed-up
(S = Ts / Tc) where Ts is the average time of the sequential version and Tc is the 
average time of the concurrent version.

The results are written to "speedup_results.txt".

Version: 1.0
Date: 2024-04-27
"""

import re
from pathlib import Path
from typing import Dict, Optional
from dataclasses import dataclass
import io


@dataclass
class MetricData:
    """Class to store metric data for a specific case."""

    average_time: float


class SpeedupCalculator:
    """Class responsible for calculating and generating speed-up reports."""

    def __init__(self, sequential_path: str, concurrent_path: str, output_path: str):
        """
        Initializes the speed-up calculator.

        Args:
            sequential_path: Path to the sequential metrics file
            concurrent_path: Path to the concurrent metrics file
            output_path: Path to the output file
        """
        self.sequential_path = Path(sequential_path)
        self.concurrent_path = Path(concurrent_path)
        self.output_path = Path(output_path)

    def read_metrics_file(self, file_path: Path) -> Dict[str, MetricData]:
        """
        Reads a metrics file and extracts the average execution time for each case.

        Args: file_path: Path to the metrics file

        Returns: Dictionary containing metric data indexed by case name
        """
        metrics: Dict[str, MetricData] = {}
        current_case: Optional[str] = None

        try:
            with open(file_path, "r", encoding="utf-8") as file:
                for line in file:
                    case_match = re.search(r"Case ([A-J])", line)
                    if case_match:
                        current_case = case_match.group(1)

                    time_match = re.search(r"Average Time \(ms\): ([\d.]+)", line)
                    if time_match and current_case:
                        avg_time = float(time_match.group(1))
                        metrics[current_case] = MetricData(average_time=avg_time)

        except FileNotFoundError:
            print(f"Error: File not found - {file_path}")
        except (io.UnsupportedOperation, PermissionError) as e:
            print(f"File access error {file_path}: {str(e)}")
        except ValueError as e:
            print(f"Error processing data from file {file_path}: {str(e)}")

        return metrics

    def calculate_speedup(self, seq_time: float, conc_time: float) -> Optional[float]:
        """
        Calculates the speed-up between sequential and concurrent times.

        Args:
            seq_time: Sequential execution time
            conc_time: Concurrent execution time

        Returns:
            Calculated speed-up or None if calculation is not possible
        """
        try:
            if conc_time <= 0:
                print(f"Warning: Invalid concurrent time detected: {conc_time}")
                return None
            return seq_time / conc_time
        except ZeroDivisionError:
            print("Error: Division by zero while calculating speed-up")
            return None

    def calculate_and_write_results(self):
        """
        Calculates the speed-up and writes the results to the output file.
        """
        sequential_metrics = self.read_metrics_file(self.sequential_path)
        concurrent_metrics = self.read_metrics_file(self.concurrent_path)

        try:
            with open(self.output_path, "w", encoding="utf-8") as output_file:
                output_file.write("Speed-up Results:\n\n")

                for case_name, seq_data in sequential_metrics.items():
                    conc_data = concurrent_metrics.get(case_name)

                    if conc_data:
                        speedup = self.calculate_speedup(
                            seq_data.average_time, conc_data.average_time
                        )

                        output_file.write(f"Case {case_name}:\n")
                        output_file.write(
                            f"Sequential Avg Time (ms): {seq_data.average_time:.4f}\n"
                        )
                        output_file.write(
                            f"Concurrent Avg Time (ms): {conc_data.average_time:.4f}\n"
                        )

                        if speedup is not None:
                            output_file.write(
                                f"Speed-up (S = Ts / Tc): {speedup:.4f}\n"
                            )
                            result = (
                                "Concurrent version is faster."
                                if speedup > 1
                                else "Sequential version is faster."
                            )
                            output_file.write(f"Result: {result}\n")
                        else:
                            output_file.write(
                                "Speed-up: Could not be calculated (invalid concurrent time)\n"
                            )
                        output_file.write("\n")

                    else:
                        output_file.write(
                            f"Case {case_name}: No data for concurrent version.\n\n"
                        )
            print(
                f"Speed-up calculation completed. Results saved in {self.output_path}"
            )

        except (io.UnsupportedOperation, PermissionError) as e:
            print(f"Error writing to output file: {str(e)}")
        except OSError as e:
            print(f"System error while handling file: {str(e)}")


def main():
    """Main function of the script."""
    # Paths to the metrics files
    sequential_metrics_path = "Sequential/output/metrics.txt"
    concurrent_metrics_path = "Concurrent/output/metrics.txt"
    output_path = "speedup_results.txt"

    # Create and execute the speed-up calculator
    calculator = SpeedupCalculator(
        sequential_metrics_path, concurrent_metrics_path, output_path
    )
    calculator.calculate_and_write_results()


if __name__ == "__main__":
    main()
