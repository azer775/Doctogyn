import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxEchartsModule } from 'ngx-echarts';
import { EChartsOption } from 'echarts';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { TokenService } from '../../Services/token.service';
import { Stat } from '../../Models/Stat';
import { ConsultationType } from '../../Models/enums';
import { Cabinet } from '../../Models/Cabinet';

@Component({
  selector: 'app-stats',
  standalone: true,
  imports: [CommonModule, FormsModule, NgxEchartsModule],
  templateUrl: './stats.component.html',
  styleUrl: './stats.component.css'
})
export class StatsComponent implements OnInit {
  stats: Stat[] = [];
  dayRange: number = 7;
  cabinet: Cabinet | null = null;

  // Chart options
  revenueChartOption: EChartsOption = {};
  consultationTypeChartOption: EChartsOption = {};
  echographyChartOption: EChartsOption = {};
  doctorChartOption: EChartsOption = {};

  constructor(
    private medicalRecordService: MedicalRecordService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.cabinet = this.tokenService.cabinet;
    this.loadStats();
  }

  loadStats(): void {
    this.medicalRecordService.getStat().subscribe({
      next: (data: any) => {
        console.log('Stats data received:', data);
        // Assuming the API returns an array of stats
        this.stats = Array.isArray(data) ? data : [data];
        console.log('Processed stats:', this.stats);
        if (this.stats.length > 0) {
          this.updateCharts();
        } else {
          console.warn('No stats data available');
        }
      },
      error: (error) => {
        console.error('Error loading stats:', error);
      }
    });
  }

  onDayRangeChange(): void {
    console.log('Day range changed to:', this.dayRange, 'Type:', typeof this.dayRange);
    // Only update the revenue chart
    this.updateRevenueChart();
  }

  filterStatsByDays(stats: Stat[]): Stat[] {
    console.log('Filtering stats with dayRange:', this.dayRange, 'Stats count:', stats.length);
    
    // If dayRange is 0 or '0', return all stats
    if (this.dayRange == 0) {
      console.log('Returning all stats');
      return stats;
    }

    const today = new Date();
    const cutoffDate = new Date();
    cutoffDate.setDate(today.getDate() - Number(this.dayRange));

    const filtered = stats.filter(stat => {
      const statDate = new Date(stat.date);
      return statDate >= cutoffDate && statDate <= today;
    });
    
    console.log('Filtered stats count:', filtered.length);
    return filtered;
  }

  updateCharts(): void {
    this.updateRevenueChart();
    this.updateConsultationTypeChart();
    this.updateEchographyChart();
    this.updateDoctorChart();
  }

  updateRevenueChart(): void {
    if (!this.cabinet) return;

    // Filter stats by date range for revenue chart only
    const filteredStats = this.filterStatsByDays(this.stats);

    if (filteredStats.length === 0) {
      console.warn('No stats available for the selected date range');
      return;
    }

    // Group by date and calculate revenue
    const revenueByDate = new Map<string, number>();

    filteredStats.forEach((stat: Stat) => {
      const dateStr = new Date(stat.date).toLocaleDateString();
      let revenue = 0;

      // Add consultation fee based on type
      switch (stat.consultationType) {
        case ConsultationType.FERTILITY:
          revenue += this.cabinet!.fertilityRate;
          break;
        case ConsultationType.GYNECOLOGY:
          revenue += this.cabinet!.gynecologyRate;
          break;
        case ConsultationType.OBSTETRICS:
          revenue += this.cabinet!.obstetricsRate;
          break;
      }

      // Add echography fees
      revenue += stat.echographyCount * this.cabinet!.echographyRate;

      const currentRevenue = revenueByDate.get(dateStr) || 0;
      revenueByDate.set(dateStr, currentRevenue + revenue);
    });

    // Sort dates properly
    const sortedEntries = Array.from(revenueByDate.entries()).sort((a, b) => {
      const dateA = new Date(a[0].split('/').reverse().join('-'));
      const dateB = new Date(b[0].split('/').reverse().join('-'));
      return dateA.getTime() - dateB.getTime();
    });

    const dates = sortedEntries.map(entry => entry[0]);
    const revenues = sortedEntries.map(entry => entry[1]);

    console.log('Revenue chart dates:', dates);
    console.log('Revenue chart values:', revenues);

    this.revenueChartOption = {
      tooltip: {
        trigger: 'axis',
        formatter: '{b}: TND{c}'
      },
      grid: {
        top: 20,
        left: 60,
        right: 40,
        bottom: 60
      },
      xAxis: {
        type: 'category',
        data: dates,
        axisLabel: {
          rotate: 45
        }
      },
      yAxis: {
        type: 'value',
        name: 'Revenue (TND)'
      },
      series: [
        {
          data: revenues,
          type: 'line',
          smooth: true,
          itemStyle: {
            color: '#6fdcdc'
          },
          lineStyle: {
            color: '#6fdcdc',
            width: 3
          },
          areaStyle: {
            color: 'rgba(111, 220, 220, 0.3)'
          },
          symbolSize: 8
        }
      ]
    };
  }

  updateConsultationTypeChart(): void {
    // Count consultations by type - use all stats
    const typeCounts = new Map<string, number>();

    this.stats.forEach((stat: Stat) => {
      const type = stat.consultationType;
      typeCounts.set(type, (typeCounts.get(type) || 0) + 1);
    });

    const data = Array.from(typeCounts.entries()).map(([name, value]) => ({
      name,
      value
    }));

    this.consultationTypeChartOption = {
      title: {
        text: 'Consultation Types Distribution',
        left: 'center',
        textStyle: {
          color: '#374151',
          fontSize: 18,
          fontWeight: 'bold'
        }
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      color: ['#6fdcdc', '#f59e0b', '#8b5cf6', '#ec4899', '#10b981', '#3b82f6'],
      series: [
        {
          type: 'pie',
          radius: '50%',
          data: data,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
  }

  updateEchographyChart(): void {
    // Group echographies by date - use all stats
    const echoByDate = new Map<string, number>();

    this.stats.forEach((stat: Stat) => {
      const dateStr = new Date(stat.date).toLocaleDateString();
      const currentCount = echoByDate.get(dateStr) || 0;
      echoByDate.set(dateStr, currentCount + stat.echographyCount);
    });

    const dates = Array.from(echoByDate.keys()).sort();
    const counts = dates.map(date => echoByDate.get(date) || 0);

    this.echographyChartOption = {
      title: {
        text: 'Echographies per Day',
        left: 'center',
        textStyle: {
          color: '#374151',
          fontSize: 18,
          fontWeight: 'bold'
        }
      },
      tooltip: {
        trigger: 'axis',
        formatter: '{b}: {c} echographies'
      },
      xAxis: {
        type: 'category',
        data: dates,
        axisLabel: {
          rotate: 45
        }
      },
      yAxis: {
        type: 'value',
        name: 'Count'
      },
      series: [
        {
          data: counts,
          type: 'line',
          smooth: true,
          itemStyle: {
            color: '#8b5cf6'
          },
          lineStyle: {
            color: '#8b5cf6',
            width: 3
          },
          areaStyle: {
            color: 'rgba(139, 92, 246, 0.3)'
          },
          symbolSize: 8
        }
      ]
    };
  }

  updateDoctorChart(): void {
    // Count consultations by doctor - use all stats
    const doctorCounts = new Map<number, number>();

    this.stats.forEach((stat: Stat) => {
      const doctorId = stat.doctorId;
      doctorCounts.set(doctorId, (doctorCounts.get(doctorId) || 0) + 1);
    });

    const doctorIds = Array.from(doctorCounts.keys()).map(id => `Doctor ${id}`);
    const counts = Array.from(doctorCounts.values());

    this.doctorChartOption = {
      title: {
        text: 'Consultations per Doctor',
        left: 'center',
        textStyle: {
          color: '#374151',
          fontSize: 18,
          fontWeight: 'bold'
        }
      },
      tooltip: {
        trigger: 'axis',
        formatter: '{b}: {c} consultations'
      },
      xAxis: {
        type: 'category',
        data: doctorIds
      },
      yAxis: {
        type: 'value',
        name: 'Consultations'
      },
      series: [
        {
          data: counts,
          type: 'bar',
          itemStyle: {
            color: '#f59e0b'
          },
          barWidth: '60%'
        }
      ]
    };
  }
}
