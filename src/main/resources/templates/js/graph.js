        const percentChangeList = [
            [[${PercentChange0}]],
            [[${PercentChange1}]],
            [[${PercentChange2}]],
            [[${PercentChange3}]],
            [[${PercentChange4}]],
            [[${PercentChange5}]],
            [[${PercentChange6}]],
            [[${PercentChange7}]],
            [[${PercentChange8}]],
            [[${PercentChange9}]],
        ];
        const priceChangeList = [
            [[${PriceChange0}]],
            [[${PriceChange1}]],
            [[${PriceChange2}]],
            [[${PriceChange3}]],
            [[${PriceChange4}]],
            [[${PriceChange5}]],
            [[${PriceChange6}]],
            [[${PriceChange7}]],
            [[${PriceChange8}]],
            [[${PriceChange9}]],
        ]
        const yearList = [
            [[${YearVal0}]],
            [[${YearVal1}]],
            [[${YearVal2}]],
            [[${YearVal3}]],
            [[${YearVal4}]],
            [[${YearVal5}]],
            [[${YearVal6}]],
            [[${YearVal7}]],
            [[${YearVal8}]],
            [[${YearVal9}]],
        ]
        Highcharts.chart('container', {
            chart: {
                type: 'column'
            },
            title: {
                text: 'Column chart with negative values'
            },
            xAxis: {
                categories: [yearList[0], yearList[1], yearList[2], yearList[3],
                yearList[4], yearList[5], yearList[6], yearList[7], yearList[8], yearList[9]]
            },
            credits: {
                enabled: false
            },
            series: [{
                name: 'Percent Value',
                data: [percentChangeList[0], percentChangeList[1], percentChangeList[2], percentChangeList[3],
                percentChangeList[4], percentChangeList[5], percentChangeList[6],
                percentChangeList[7], percentChangeList[8], percentChangeList[9]]
            }]

        });
