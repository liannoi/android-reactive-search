package org.itstep.liannoi.reactivesearch.application.common.storage

import org.itstep.liannoi.reactivesearch.application.storage.core.SampleDataSeeder

interface Seeder {

    fun seedAll(handler: SampleDataSeeder.Handler)
}
