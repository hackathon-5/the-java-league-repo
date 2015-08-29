use utf8;
package HolyGhost::DB::Schema::Result::Sighting;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

use strict;
use warnings;

use base 'DBIx::Class::Core';
__PACKAGE__->table("sighting");
__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "sighting_id_seq",
  },
  "account_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "lat",
  { data_type => "double precision", is_nullable => 0 },
  "lon",
  { data_type => "double precision", is_nullable => 0 },
  "seen_date",
  {
    data_type     => "timestamp with time zone",
    default_value => \"current_timestamp",
    is_nullable   => 0,
    original      => { default_value => \"now()" },
  },
  "title",
  { data_type => "varchar", is_nullable => 0, size => 128 },
  "rating",
  { data_type => "smallint", is_nullable => 1 },
  "description",
  { data_type => "text", is_nullable => 1 },
);
__PACKAGE__->set_primary_key("id");
__PACKAGE__->belongs_to(
  "account",
  "HolyGhost::DB::Schema::Result::Account",
  { id => "account_id" },
  { is_deferrable => 0, on_delete => "CASCADE", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07043 @ 2015-08-29 11:34:48
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:cgIkrH7fGVxw/IOL7QPKtA

__PACKAGE__->load_components(qw/PK::Auto TimeStamp/);
__PACKAGE__->add_columns('+seen_date' => {set_on_create => 1});

1;
