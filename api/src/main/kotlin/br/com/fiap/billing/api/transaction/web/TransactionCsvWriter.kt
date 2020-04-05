package br.com.fiap.billing.api.transaction.web

import br.com.fiap.billing.api.transaction.Transaction
import com.opencsv.CSVWriter
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.StatefulBeanToCsv
import com.opencsv.bean.StatefulBeanToCsvBuilder
import com.opencsv.exceptions.CsvException
import org.hibernate.bytecode.BytecodeLogger
import org.springframework.stereotype.Component
import java.io.PrintWriter
import java.time.LocalDate
import javax.validation.constraints.NotNull

@Component
class TransactionCsvWriter {

    fun write(writer: PrintWriter?, transactions: List<Transaction?>?) {
        try {
            writer?.append("Aluno, Matricula, Documento, Data, Valor, Parcelas\n")

            val mapStrategy: ColumnPositionMappingStrategy<TransactionCsv> = ColumnPositionMappingStrategy<TransactionCsv>()
            mapStrategy.setType(TransactionCsv::class.java)

            val columns = arrayOf("name", "enrollment", "doc", "value", "date", "installments")

            mapStrategy.setColumnMapping(*columns)

            val csv: StatefulBeanToCsv<TransactionCsv> = StatefulBeanToCsvBuilder<TransactionCsv>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator(',')
                    .build()

            val csvTransactions = transactions?.map { TransactionCsv(
                    name = it?.person?.name,
                    enrollment = it?.person?.enrollment,
                    doc = it?.person?.doc,
                    date = it?.transactionDate,
                    value = it?.transactionValue,
                    installments = it?.installments) }

            csv.write(csvTransactions)
        } catch (ex: CsvException) {
            BytecodeLogger.LOGGER.error("Error mapping Bean to CSV", ex)
        }
    }

    data class TransactionCsv (
            val name: String?,
            val doc: String?,
            val enrollment: String?,
            val date: LocalDate?,
            val value: Double?,
            val installments: Int?
    )
}